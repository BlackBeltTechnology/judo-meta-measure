/**
 */
package hu.blackbelt.judo.meta.measure.impl;

import hu.blackbelt.judo.meta.measure.Measure;
import hu.blackbelt.judo.meta.measure.MeasurePackage;
import hu.blackbelt.judo.meta.measure.Unit;

import java.math.BigDecimal;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Measure</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link hu.blackbelt.judo.meta.measure.impl.MeasureImpl#getName <em>Name</em>}</li>
 *   <li>{@link hu.blackbelt.judo.meta.measure.impl.MeasureImpl#getRateDividend <em>Rate Dividend</em>}</li>
 *   <li>{@link hu.blackbelt.judo.meta.measure.impl.MeasureImpl#getRateDivisor <em>Rate Divisor</em>}</li>
 *   <li>{@link hu.blackbelt.judo.meta.measure.impl.MeasureImpl#getUnits <em>Units</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class MeasureImpl extends MinimalEObjectImpl.Container implements Measure {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getRateDividend() <em>Rate Dividend</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRateDividend()
	 * @generated
	 * @ordered
	 */
	protected static final BigDecimal RATE_DIVIDEND_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRateDividend() <em>Rate Dividend</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRateDividend()
	 * @generated
	 * @ordered
	 */
	protected BigDecimal rateDividend = RATE_DIVIDEND_EDEFAULT;

	/**
	 * The default value of the '{@link #getRateDivisor() <em>Rate Divisor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRateDivisor()
	 * @generated
	 * @ordered
	 */
	protected static final BigDecimal RATE_DIVISOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRateDivisor() <em>Rate Divisor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRateDivisor()
	 * @generated
	 * @ordered
	 */
	protected BigDecimal rateDivisor = RATE_DIVISOR_EDEFAULT;

	/**
	 * The cached value of the '{@link #getUnits() <em>Units</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUnits()
	 * @generated
	 * @ordered
	 */
	protected EList<Unit> units;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MeasureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MeasurePackage.Literals.MEASURE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MeasurePackage.MEASURE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigDecimal getRateDividend() {
		return rateDividend;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRateDividend(BigDecimal newRateDividend) {
		BigDecimal oldRateDividend = rateDividend;
		rateDividend = newRateDividend;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MeasurePackage.MEASURE__RATE_DIVIDEND, oldRateDividend, rateDividend));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigDecimal getRateDivisor() {
		return rateDivisor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRateDivisor(BigDecimal newRateDivisor) {
		BigDecimal oldRateDivisor = rateDivisor;
		rateDivisor = newRateDivisor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MeasurePackage.MEASURE__RATE_DIVISOR, oldRateDivisor, rateDivisor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Unit> getUnits() {
		if (units == null) {
			units = new EObjectResolvingEList<Unit>(Unit.class, this, MeasurePackage.MEASURE__UNITS);
		}
		return units;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MeasurePackage.MEASURE__NAME:
				return getName();
			case MeasurePackage.MEASURE__RATE_DIVIDEND:
				return getRateDividend();
			case MeasurePackage.MEASURE__RATE_DIVISOR:
				return getRateDivisor();
			case MeasurePackage.MEASURE__UNITS:
				return getUnits();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MeasurePackage.MEASURE__NAME:
				setName((String)newValue);
				return;
			case MeasurePackage.MEASURE__RATE_DIVIDEND:
				setRateDividend((BigDecimal)newValue);
				return;
			case MeasurePackage.MEASURE__RATE_DIVISOR:
				setRateDivisor((BigDecimal)newValue);
				return;
			case MeasurePackage.MEASURE__UNITS:
				getUnits().clear();
				getUnits().addAll((Collection<? extends Unit>)newValue);
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
			case MeasurePackage.MEASURE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case MeasurePackage.MEASURE__RATE_DIVIDEND:
				setRateDividend(RATE_DIVIDEND_EDEFAULT);
				return;
			case MeasurePackage.MEASURE__RATE_DIVISOR:
				setRateDivisor(RATE_DIVISOR_EDEFAULT);
				return;
			case MeasurePackage.MEASURE__UNITS:
				getUnits().clear();
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
			case MeasurePackage.MEASURE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case MeasurePackage.MEASURE__RATE_DIVIDEND:
				return RATE_DIVIDEND_EDEFAULT == null ? rateDividend != null : !RATE_DIVIDEND_EDEFAULT.equals(rateDividend);
			case MeasurePackage.MEASURE__RATE_DIVISOR:
				return RATE_DIVISOR_EDEFAULT == null ? rateDivisor != null : !RATE_DIVISOR_EDEFAULT.equals(rateDivisor);
			case MeasurePackage.MEASURE__UNITS:
				return units != null && !units.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", rateDividend: ");
		result.append(rateDividend);
		result.append(", rateDivisor: ");
		result.append(rateDivisor);
		result.append(')');
		return result.toString();
	}

} //MeasureImpl
