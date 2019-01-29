/**
 */
package hu.blackbelt.judo.meta.measure;

import java.math.BigDecimal;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link hu.blackbelt.judo.meta.measure.Unit#getName <em>Name</em>}</li>
 *   <li>{@link hu.blackbelt.judo.meta.measure.Unit#getRateDividend <em>Rate Dividend</em>}</li>
 *   <li>{@link hu.blackbelt.judo.meta.measure.Unit#getRateDivisor <em>Rate Divisor</em>}</li>
 * </ul>
 *
 * @see hu.blackbelt.judo.meta.measure.MeasurePackage#getUnit()
 * @model
 * @generated
 */
public interface Unit extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see hu.blackbelt.judo.meta.measure.MeasurePackage#getUnit_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link hu.blackbelt.judo.meta.measure.Unit#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Rate Dividend</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rate Dividend</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rate Dividend</em>' attribute.
	 * @see #setRateDividend(BigDecimal)
	 * @see hu.blackbelt.judo.meta.measure.MeasurePackage#getUnit_RateDividend()
	 * @model required="true"
	 * @generated
	 */
	BigDecimal getRateDividend();

	/**
	 * Sets the value of the '{@link hu.blackbelt.judo.meta.measure.Unit#getRateDividend <em>Rate Dividend</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rate Dividend</em>' attribute.
	 * @see #getRateDividend()
	 * @generated
	 */
	void setRateDividend(BigDecimal value);

	/**
	 * Returns the value of the '<em><b>Rate Divisor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rate Divisor</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rate Divisor</em>' attribute.
	 * @see #setRateDivisor(BigDecimal)
	 * @see hu.blackbelt.judo.meta.measure.MeasurePackage#getUnit_RateDivisor()
	 * @model required="true"
	 * @generated
	 */
	BigDecimal getRateDivisor();

	/**
	 * Sets the value of the '{@link hu.blackbelt.judo.meta.measure.Unit#getRateDivisor <em>Rate Divisor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rate Divisor</em>' attribute.
	 * @see #getRateDivisor()
	 * @generated
	 */
	void setRateDivisor(BigDecimal value);

} // Unit
