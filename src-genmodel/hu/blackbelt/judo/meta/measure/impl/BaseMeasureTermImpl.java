/**
 */
package hu.blackbelt.judo.meta.measure.impl;

import hu.blackbelt.judo.meta.measure.BaseMeasure;
import hu.blackbelt.judo.meta.measure.BaseMeasureTerm;
import hu.blackbelt.judo.meta.measure.MeasurePackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Base Measure Term</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link hu.blackbelt.judo.meta.measure.impl.BaseMeasureTermImpl#getExponent <em>Exponent</em>}</li>
 *   <li>{@link hu.blackbelt.judo.meta.measure.impl.BaseMeasureTermImpl#getBaseMeasure <em>Base Measure</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BaseMeasureTermImpl extends MinimalEObjectImpl.Container implements BaseMeasureTerm {
	/**
	 * The default value of the '{@link #getExponent() <em>Exponent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExponent()
	 * @generated
	 * @ordered
	 */
	protected static final int EXPONENT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getExponent() <em>Exponent</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExponent()
	 * @generated
	 * @ordered
	 */
	protected int exponent = EXPONENT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBaseMeasure() <em>Base Measure</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBaseMeasure()
	 * @generated
	 * @ordered
	 */
	protected BaseMeasure baseMeasure;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BaseMeasureTermImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MeasurePackage.Literals.BASE_MEASURE_TERM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getExponent() {
		return exponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExponent(int newExponent) {
		int oldExponent = exponent;
		exponent = newExponent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MeasurePackage.BASE_MEASURE_TERM__EXPONENT, oldExponent, exponent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseMeasure getBaseMeasure() {
		if (baseMeasure != null && baseMeasure.eIsProxy()) {
			InternalEObject oldBaseMeasure = (InternalEObject)baseMeasure;
			baseMeasure = (BaseMeasure)eResolveProxy(oldBaseMeasure);
			if (baseMeasure != oldBaseMeasure) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MeasurePackage.BASE_MEASURE_TERM__BASE_MEASURE, oldBaseMeasure, baseMeasure));
			}
		}
		return baseMeasure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseMeasure basicGetBaseMeasure() {
		return baseMeasure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBaseMeasure(BaseMeasure newBaseMeasure) {
		BaseMeasure oldBaseMeasure = baseMeasure;
		baseMeasure = newBaseMeasure;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MeasurePackage.BASE_MEASURE_TERM__BASE_MEASURE, oldBaseMeasure, baseMeasure));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MeasurePackage.BASE_MEASURE_TERM__EXPONENT:
				return getExponent();
			case MeasurePackage.BASE_MEASURE_TERM__BASE_MEASURE:
				if (resolve) return getBaseMeasure();
				return basicGetBaseMeasure();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MeasurePackage.BASE_MEASURE_TERM__EXPONENT:
				setExponent((Integer)newValue);
				return;
			case MeasurePackage.BASE_MEASURE_TERM__BASE_MEASURE:
				setBaseMeasure((BaseMeasure)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MeasurePackage.BASE_MEASURE_TERM__EXPONENT:
				setExponent(EXPONENT_EDEFAULT);
				return;
			case MeasurePackage.BASE_MEASURE_TERM__BASE_MEASURE:
				setBaseMeasure((BaseMeasure)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MeasurePackage.BASE_MEASURE_TERM__EXPONENT:
				return exponent != EXPONENT_EDEFAULT;
			case MeasurePackage.BASE_MEASURE_TERM__BASE_MEASURE:
				return baseMeasure != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (exponent: ");
		result.append(exponent);
		result.append(')');
		return result.toString();
	}

} //BaseMeasureTermImpl
