/**
 */
package hu.blackbelt.judo.meta.measure;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see hu.blackbelt.judo.meta.measure.MeasurePackage
 * @generated
 */
public interface MeasureFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MeasureFactory eINSTANCE = hu.blackbelt.judo.meta.measure.impl.MeasureFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Unit</em>'.
	 * @generated
	 */
	Unit createUnit();

	/**
	 * Returns a new object of class '<em>Derived Measure</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Derived Measure</em>'.
	 * @generated
	 */
	DerivedMeasure createDerivedMeasure();

	/**
	 * Returns a new object of class '<em>Base Measure</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Base Measure</em>'.
	 * @generated
	 */
	BaseMeasure createBaseMeasure();

	/**
	 * Returns a new object of class '<em>Base Measure Term</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Base Measure Term</em>'.
	 * @generated
	 */
	BaseMeasureTerm createBaseMeasureTerm();

	/**
	 * Returns a new object of class '<em>Duration Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Duration Unit</em>'.
	 * @generated
	 */
	DurationUnit createDurationUnit();

	/**
	 * Returns an instance of data type '<em>Duration Type</em>' corresponding the given literal.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal a literal of the data type.
	 * @return a new instance value of the data type.
	 * @generated
	 */
	DurationType createDurationType(String literal);

	/**
	 * Returns a literal representation of an instance of data type '<em>Duration Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param instanceValue an instance value of the data type.
	 * @return a literal representation of the instance value.
	 * @generated
	 */
	String convertDurationType(DurationType instanceValue);

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	MeasurePackage getMeasurePackage();

} //MeasureFactory
