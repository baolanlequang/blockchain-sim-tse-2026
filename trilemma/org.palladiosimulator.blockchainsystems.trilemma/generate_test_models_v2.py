import pandas as pd
import shutil
import time
import xml.etree.ElementTree as ET
import uuid

# === NEW: files used to trace which configurations are used vs skipped ===
# These files allow downstream analysis to correctly map simulation results
# back to the original CSV parameter configurations.
USED_CONFIGS_LOG = "used_configurations.csv"
SKIPPED_CONFIGS_LOG = "skipped_configurations.csv"

pcm_namespaces = {
  'blockchainsystemComponentRepository': 'http://palladiosimulator.org/BlockchainSystemComponentModel/BlockchainSystemComponentRepository/1.0'
}

def register_all_namespaces(filename):
  namespaces = dict([node for _, node in ET.iterparse(filename, events=['start-ns'])])
  for ns in namespaces:
    ET.register_namespace(ns, namespaces[ns])

def read_and_generate_raw_data():

  # === NEW: initialize traceability logs (overwrite on each run) ===
  # This ensures a clean mapping between one generator run and simulation results.
  with open(USED_CONFIGS_LOG, "w") as f:
    f.write("config_id\n")

  with open(SKIPPED_CONFIGS_LOG, "w") as f:
    f.write("config_id,reason\n")

  csv_data = pd.read_csv('optimized_deterministic_lhs_configurations.csv')

  for index, row in csv_data.iterrows():
    # print(row)
    config_id = str(int(row['config_id']))
    shutil.copytree(
      'testmodels/threesim-net-base',
      'testmodels/threesim-' + config_id,
      dirs_exist_ok=True
    )

  time.sleep(1) # wait 1 second to continue to modify data

  for index, row in csv_data.iterrows():
    config_id = str(int(row['config_id']))
    modify_data('testmodels/threesim-' + config_id, 'Net', row)

def modify_data(folder, prefix_name, parameter_data):

  # === SAFETY FILTER ===
  # Skip configurations that are known to produce invalid Threesim states
  if (
      parameter_data['bandwidth'] <= 0 or
      parameter_data['workload'] <= 0 or
      parameter_data['block_creation_interval'] <= 0 or
      parameter_data['validator_count'] <= parameter_data['crashed_validators']
  ):
      config_id = int(parameter_data['config_id'])

      # === NEW: log skipped configuration with reason ===
      with open(SKIPPED_CONFIGS_LOG, "a") as f:
        f.write(f"{config_id},invalid_parameter_combination\n")

      print(f"[SKIP] Invalid config_id={config_id}")
      return
  # === END SAFETY FILTER ===

  link_allocation_file_path = folder + '/' + prefix_name + '.linkallocation'
  blockchainsystemm_file_path = folder + '/' + prefix_name + '.blockchainsystem'
  node_allocation_file_path = folder + '/' + prefix_name + '.nodeallocation'
  transaction_file_path = folder + '/' + prefix_name + '.transactions'
  p2pnetwork_file_path = folder + '/' + prefix_name + '.p2pnetwork'

  # Process link allocation
  modify_link_allocation(link_allocation_file_path, parameter_data)

  # Process blockchain system
  modify_blockchain_system(blockchainsystemm_file_path, parameter_data)

  # Process node allocation
  crashed_allocation_id = modify_node_allocation(node_allocation_file_path, parameter_data)

  time.sleep(0.0001) # wait 1 second to continue to modify data
  if (crashed_allocation_id != ''):
    modify_p2p_network(p2pnetwork_file_path, crashed_allocation_id, parameter_data)

  # Process transaction
  modify_transaction(transaction_file_path, parameter_data)

  # === NEW: log successfully used configuration ===
  # Only configurations that fully generate valid models are logged here.
  config_id = int(parameter_data['config_id'])
  with open(USED_CONFIGS_LOG, "a") as f:
    f.write(f"{config_id}\n")

def modify_link_allocation(file_path, parameter_data):
  register_all_namespaces(file_path)
  xml_tree = ET.parse(file_path)
  xml_root = xml_tree.getroot()

  # Net.linkallocation -> bandwidthSpecification/@Bandwidth is an EMF ELong.
  # Pandas reads CSV numbers as float (e.g., 95.2949...), but EMF integer
  # types (EInt/ELong) cannot contain decimals and will fail to load.
  # Cast to int before writing to ensure valid EMF serialization.
  for bandwidth in xml_root.iter('bandwidthSpecification'):
    bandwidth.attrib['Bandwidth'] = str(int(parameter_data['bandwidth']))  # ### CHANGED

  xml_tree.write(file_path)

def modify_blockchain_system(file_path, parameter_data):
  register_all_namespaces(file_path)
  xml_tree = ET.parse(file_path)
  xml_root = xml_tree.getroot()

  # Prevent zero block interval
  block_interval_ms = max(1, int(parameter_data['block_creation_interval'] * 1000))  # ### CHANGED
  max_block_size_byte = int(parameter_data['max_block_size'] * 1000000)

  for specification in xml_root.iter('Specification'):
    specification.attrib['MeanBlockTime'] = str(block_interval_ms)
    specification.attrib['MaxBlockSize'] = str(max_block_size_byte)

  xml_tree.write(file_path)

def modify_node_allocation(file_path, parameter_data):

  crashed_allocation_id = ""

  register_all_namespaces(file_path)
  xml_tree = ET.parse(file_path)
  xml_root = xml_tree.getroot()

  hashrate = parameter_data['hashing_power']
  validator_count = int(parameter_data['validator_count'])
  crashed_validators = int(parameter_data['crashed_validators'])

  # Prevent all validators from crashing
  # Ensure at least one validator remains alive
  crashed_validators = min(crashed_validators, validator_count - 1)  # ### CHANGED

  # Apply crashes AFTER clamping
  validator_count -= crashed_validators  # ### NEW (CRITICAL)

  for node_allo in xml_root.iter('NodeAllocations'):
    allocation_name = node_allo.attrib['entityName']
    if (crashed_validators > 0 and allocation_name == "HasCrashes"):
      crashed_allocation_id = node_allo.attrib['id']
      for node_system in node_allo.iter('NodeSystem'):
        validator_id = node_system.attrib['BlockValidatorAssembly']
        crash_validator_id = ''
        for context in node_system.iter('AssemblyContexts'):
          if context.attrib['entityName'] == 'CrashedValidator':
            crash_validator_id  = context.attrib['id']

          for comp in context.iter('EncapsulatedComponent'):
            comp.tag = f"{{{pcm_namespaces['blockchainsystemComponentRepository']}}}{comp.tag}"

      for resource_container in node_allo.iter('ResourceContainers'):
        resource_container.attrib['ResourcePower'] = str(hashrate)
        resource_id = resource_container.attrib['id']
        break

      for idx in range(1, validator_count):
        new_context = ET.SubElement(node_allo, 'AllocationContexts')
        new_context.attrib['id'] = '_' + uuid.uuid4().hex
        new_context.attrib['AssemblyContext'] = validator_id
        new_context.attrib['ResourceContainer'] = resource_id

      for idx in range(1, crashed_validators):
        new_context = ET.SubElement(node_allo, 'AllocationContexts')
        new_context.attrib['id'] = '_' + uuid.uuid4().hex
        new_context.attrib['AssemblyContext'] = crash_validator_id
        new_context.attrib['ResourceContainer'] = resource_id

    else:
      for node_system in node_allo.iter('NodeSystem'):
        validator_id = node_system.attrib['BlockValidatorAssembly']
        for context in node_system.iter('AssemblyContexts'):
          for comp in context.iter('EncapsulatedComponent'):
            comp.tag = f"{{{pcm_namespaces['blockchainsystemComponentRepository']}}}{comp.tag}"

      for resource_container in node_allo.iter('ResourceContainers'):
        resource_container.attrib['ResourcePower'] = str(hashrate)
        resource_id = resource_container.attrib['id']
        break

      for idx in range(1, validator_count):
        new_context = ET.SubElement(node_allo, 'AllocationContexts')
        new_context.attrib['id'] = '_' + uuid.uuid4().hex
        new_context.attrib['AssemblyContext'] = validator_id
        new_context.attrib['ResourceContainer'] = resource_id

  ET.indent(xml_tree, space="\t", level=0)
  xml_tree.write(file_path, xml_declaration=True, encoding="utf-8")
  return crashed_allocation_id

def modify_p2p_network(file_path, node_allocation_id, parameter_data):
  register_all_namespaces(file_path)
  xml_tree = ET.parse(file_path)
  xml_root = xml_tree.getroot()

  for alllocation in xml_root.iter('Allocation'):
    curr_href = alllocation.attrib['href']
    curr_href_split = curr_href.split("#")
    curr_href_split[1] = node_allocation_id
    alllocation.attrib['href'] = "#".join(curr_href_split)

  # IMPORTANT:
  # NumberOfInbound / NumberOfOutBound are EMF EInt attributes.
  # Pandas loads CSV numeric values as floats (e.g., 43.0), but EMF
  # rejects decimals for EInt and fails with IllegalValueException.
  # We must cast to int here to ensure valid EMF serialization.
  inbound_connectivity = int(parameter_data['inbound_connectivity'])   # ### CHANGED
  outbound_connectivity = int(parameter_data['outbound_connectivity']) # ### CHANGED

  for subgraph in xml_root.iter('Subgraphs'):
    for nodetemplate in subgraph.iter('NodeTemplates'):
      nodetemplate.attrib['NumberOfNodeOccurences'] = str(
        inbound_connectivity + outbound_connectivity
      )

    for connectivitySpecification in subgraph.iter('ConnectivitySpecification'):
      connectivitySpecification.attrib['NumberOfInbound'] = str(inbound_connectivity)
      connectivitySpecification.attrib['NumberOfOutBound'] = str(outbound_connectivity)

  xml_tree.write(file_path)

def modify_transaction(file_path, parameter_data):
  register_all_namespaces(file_path)
  xml_tree = ET.parse(file_path)
  xml_root = xml_tree.getroot()

  # Prevent non zero or negetive workload values
  workload_txs_per_day = max(1, int(parameter_data['workload']))  # ### CHANGED

  transaction_time_in_sec = 86400 / workload_txs_per_day
  transaction_time_in_ms = transaction_time_in_sec * 1000
  xml_root.attrib['MeanTransactionCreationInterval'] = str(transaction_time_in_ms)

  xml_tree.write(file_path)

read_and_generate_raw_data()
