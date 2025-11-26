/**
 */
package org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mining Process Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.MiningProcessComponent#isIsMiningProcessEnabled <em>Is Mining Process Enabled</em>}</li>
 * </ul>
 *
 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryPackage#getMiningProcessComponent()
 * @model
 * @generated
 */
public interface MiningProcessComponent extends BlockchainSystemNodeComponent {
	/**
	 * Returns the value of the '<em><b>Is Mining Process Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Mining Process Enabled</em>' attribute.
	 * @see #setIsMiningProcessEnabled(boolean)
	 * @see org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.BlockchainsystemComponentRepositoryPackage#getMiningProcessComponent_IsMiningProcessEnabled()
	 * @model required="true"
	 * @generated
	 */
	boolean isIsMiningProcessEnabled();

	/**
	 * Sets the value of the '{@link org.palladiosimulator.blockchainsystems.bscm.blockchainsystemComponentRepository.MiningProcessComponent#isIsMiningProcessEnabled <em>Is Mining Process Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Mining Process Enabled</em>' attribute.
	 * @see #isIsMiningProcessEnabled()
	 * @generated
	 */
	void setIsMiningProcessEnabled(boolean value);

} // MiningProcessComponent
