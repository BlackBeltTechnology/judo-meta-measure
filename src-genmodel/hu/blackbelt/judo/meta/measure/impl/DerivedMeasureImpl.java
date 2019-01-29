/**
 */
package hu.blackbelt.judo.meta.measure.impl;

import hu.blackbelt.judo.meta.measure.BaseMeasureTerm;
import hu.blackbelt.judo.meta.measure.DerivedMeasure;
import hu.blackbelt.judo.meta.measure.MeasurePackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Derived Measure</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link hu.blackbelt.judo.meta.measure.impl.DerivedMeasureImpl#getTerms <em>Terms</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DerivedMeasureImpl extends MeasureImpl implements DerivedMeasure {
	/**
	 * The cached value of the '{@link #getTerms() <em>Terms</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTerms()
	 * @generated
	 * @ordered
	 */
	protected BaseMeasureTerm terms;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DerivedMeasureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MeasurePackage.Literals.DERIVED_MEASURE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseMeasureTerm getTerms() {
		return terms;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTerms(BaseMeasureTerm newTerms, NotificationChain msgs) {
		BaseMeasureTerm oldTerms = terms;
		terms = newTerms;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, MeasurePackage.DERIVED_MEASURE__TERMS, oldTerms, newTerms);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTerms(BaseMeasureTerm newTerms) {
		if (newTerms != terms) {
			NotificationChain msgs = null;
			if (terms != null)
				msgs = ((InternalEObject)terms).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - MeasurePackage.DERIVED_MEASURE__TERMS, null, msgs);
			if (newTerms != null)
				msgs = ((InternalEObject)newTerms).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - MeasurePackage.DERIVED_MEASURE__TERMS, null, msgs);
			msgs = basicSetTerms(newTerms, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MeasurePackage.DERIVED_MEASURE__TERMS, newTerms, newTerms));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MeasurePackage.DERIVED_MEASURE__TERMS:
				return basicSetTerms(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MeasurePackage.DERIVED_MEASURE__TERMS:
				return getTerms();
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
			case MeasurePackage.DERIVED_MEASURE__TERMS:
				setTerms((BaseMeasureTerm)newValue);
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
			case MeasurePackage.DERIVED_MEASURE__TERMS:
				setTerms((BaseMeasureTerm)null);
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
			case MeasurePackage.DERIVED_MEASURE__TERMS:
				return terms != null;
		}
		return super.eIsSet(featureID);
	}

} //DerivedMeasureImpl
