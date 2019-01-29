/**
 */
package hu.blackbelt.judo.meta.measure;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Base Measure Term</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link hu.blackbelt.judo.meta.measure.BaseMeasureTerm#getExponent <em>Exponent</em>}</li>
 *   <li>{@link hu.blackbelt.judo.meta.measure.BaseMeasureTerm#getBaseMeasure <em>Base Measure</em>}</li>
 * </ul>
 *
 * @see hu.blackbelt.judo.meta.measure.MeasurePackage#getBaseMeasureTerm()
 * @model
 * @generated
 */
public interface BaseMeasureTerm extends EObject {
	/**
	 * Returns the value of the '<em><b>Exponent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exponent</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exponent</em>' attribute.
	 * @see #setExponent(int)
	 * @see hu.blackbelt.judo.meta.measure.MeasurePackage#getBaseMeasureTerm_Exponent()
	 * @model required="true"
	 * @generated
	 */
	int getExponent();

	/**
	 * Sets the value of the '{@link hu.blackbelt.judo.meta.measure.BaseMeasureTerm#getExponent <em>Exponent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exponent</em>' attribute.
	 * @see #getExponent()
	 * @generated
	 */
	void setExponent(int value);

	/**
	 * Returns the value of the '<em><b>Base Measure</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Base Measure</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Base Measure</em>' reference.
	 * @see #setBaseMeasure(BaseMeasure)
	 * @see hu.blackbelt.judo.meta.measure.MeasurePackage#getBaseMeasureTerm_BaseMeasure()
	 * @model required="true"
	 * @generated
	 */
	BaseMeasure getBaseMeasure();

	/**
	 * Sets the value of the '{@link hu.blackbelt.judo.meta.measure.BaseMeasureTerm#getBaseMeasure <em>Base Measure</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Base Measure</em>' reference.
	 * @see #getBaseMeasure()
	 * @generated
	 */
	void setBaseMeasure(BaseMeasure value);

} // BaseMeasureTerm
