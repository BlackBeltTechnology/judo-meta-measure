/**
 */
package hu.blackbelt.judo.meta.measure;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Derived Measure</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link hu.blackbelt.judo.meta.measure.DerivedMeasure#getTerms <em>Terms</em>}</li>
 * </ul>
 *
 * @see hu.blackbelt.judo.meta.measure.MeasurePackage#getDerivedMeasure()
 * @model
 * @generated
 */
public interface DerivedMeasure extends Measure {
	/**
	 * Returns the value of the '<em><b>Terms</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Terms</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Terms</em>' containment reference.
	 * @see #setTerms(BaseMeasureTerm)
	 * @see hu.blackbelt.judo.meta.measure.MeasurePackage#getDerivedMeasure_Terms()
	 * @model containment="true" required="true"
	 * @generated
	 */
	BaseMeasureTerm getTerms();

	/**
	 * Sets the value of the '{@link hu.blackbelt.judo.meta.measure.DerivedMeasure#getTerms <em>Terms</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Terms</em>' containment reference.
	 * @see #getTerms()
	 * @generated
	 */
	void setTerms(BaseMeasureTerm value);

} // DerivedMeasure
