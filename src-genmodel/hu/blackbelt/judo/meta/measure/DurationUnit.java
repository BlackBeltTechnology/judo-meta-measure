/**
 */
package hu.blackbelt.judo.meta.measure;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Duration Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link hu.blackbelt.judo.meta.measure.DurationUnit#getType <em>Type</em>}</li>
 * </ul>
 *
 * @see hu.blackbelt.judo.meta.measure.MeasurePackage#getDurationUnit()
 * @model
 * @generated
 */
public interface DurationUnit extends Unit {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link hu.blackbelt.judo.meta.measure.DurationType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see hu.blackbelt.judo.meta.measure.DurationType
	 * @see #setType(DurationType)
	 * @see hu.blackbelt.judo.meta.measure.MeasurePackage#getDurationUnit_Type()
	 * @model required="true"
	 * @generated
	 */
	DurationType getType();

	/**
	 * Sets the value of the '{@link hu.blackbelt.judo.meta.measure.DurationUnit#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see hu.blackbelt.judo.meta.measure.DurationType
	 * @see #getType()
	 * @generated
	 */
	void setType(DurationType value);

} // DurationUnit
