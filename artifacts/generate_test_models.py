import pandas as pd
import shutil
import time
import xml.etree.ElementTree as ET
import uuid

pcm_namespaces = {
  'blockchainsystemComponentRepository': 'http://palladiosimulator.org/BlockchainSystemComponentModel/BlockchainSystemComponentRepository/1.0'
}

def register_all_namespaces(filename):
  namespaces = dict([node for _, node in ET.iterparse(filename, events=['start-ns'])])
  for ns in namespaces:
    ET.register_namespace(ns, namespaces[ns])

def read_and_generate_raw_data():
  csv_data = pd.read_csv('optimized_deterministic_lhs_configurations.csv')
  for index, row in csv_data.iterrows():
    # print(row)
    config_id = str(row['config_id'])
    if row['peer_connectivity'] == 'Net':
      # generate for Net topology
      shutil.copytree('testmodels/threesim-net-base', 'testmodels/net/threesim-net-'+config_id, dirs_exist_ok=True)
    else:
      # generate for Ring topology
      shutil.copytree('testmodels/threesim-ring-base', 'testmodels/ring/threesim-ring-'+config_id, dirs_exist_ok=True)
      
  time.sleep(1) # wait 1 second to continue to modify da
  for index, row in csv_data.iterrows():
    config_id = str(row['config_id'])
    if row['peer_connectivity'] == 'Net':
      modify_data('testmodels/net/threesim-net-'+config_id, 'Net', row)
    else:
      modify_data('testmodels/ring/threesim-ring-'+config_id, 'Ring', row)
  

def modify_data(folder, prefix_name, parameter_data):
  link_allocation_file_path = folder + '/' + prefix_name + '.linkallocation'
  blockchainsystemm_file_path = folder + '/' + prefix_name + '.blockchainsystem'
  node_allocation_file_path = folder + '/' + prefix_name + '.nodeallocation'
  transaction_file_path = folder + '/' + prefix_name + '.transactions'
  # Process link allocation
  modify_link_allocation(link_allocation_file_path, parameter_data)
  
  # Process blockchain system
  modify_blockchain_system(blockchainsystemm_file_path, parameter_data)
  
  # Process node allocation
  modify_node_allocation(node_allocation_file_path, parameter_data)
  
  # Process transaction
  modify_transaction(transaction_file_path, parameter_data)
  
def modify_link_allocation(file_path, parameter_data):
  register_all_namespaces(file_path)
  xml_tree = ET.parse(file_path)
  xml_root = xml_tree.getroot()
  for bandwidth in xml_root.iter('bandwidthSpecification'):
    bandwidth.attrib['Bandwidth'] = str(parameter_data['bandwidth_mbps'])
  xml_tree.write(file_path)
  
def modify_blockchain_system(file_path, parameter_data):
  register_all_namespaces(file_path)
  xml_tree = ET.parse(file_path)
  xml_root = xml_tree.getroot()
  block_interval_ms = parameter_data['block_interval_s'] * 1000
  max_block_size_byte = int(parameter_data['max_block_size_MB'] * 1000000)
  for specification in xml_root.iter('Specification'):
    specification.attrib['MeanBlockTime'] = str(block_interval_ms)
    specification.attrib['MaxBlockSize'] = str(max_block_size_byte)
  xml_tree.write(file_path)
  
def modify_node_allocation(file_path, parameter_data):
      
  register_all_namespaces(file_path)
  xml_tree = ET.parse(file_path)
  xml_root = xml_tree.getroot()
 
  hashrate = parameter_data['hashrate']
  validator_count = parameter_data['validator_count']
  for node_allo in xml_root.iter('NodeAllocations'):
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
  
def modify_transaction(file_path, parameter_data):
  register_all_namespaces(file_path)
  xml_tree = ET.parse(file_path)
  xml_root = xml_tree.getroot()
  workload_txs_per_day = parameter_data['workload_txs_per_day']
  transaction_time_in_sec = 86400 / workload_txs_per_day
  transaction_time_in_ms = transaction_time_in_sec * 1000
  xml_root.attrib['MeanTransactionCreationInterval'] = str(transaction_time_in_ms)
  xml_tree.write(file_path)
  
read_and_generate_raw_data()