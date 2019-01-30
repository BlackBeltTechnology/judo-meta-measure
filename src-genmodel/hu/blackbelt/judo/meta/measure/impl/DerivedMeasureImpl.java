/**
 */
package hu.blackbelt.judo.meta.measure.impl;

import hu.blackbelt.judo.meta.measure.BaseMeasureTerm;
import hu.blackbelt.judo.meta.measure.DerivedMeasure;
import hu.blackbelt.judo.meta.measure.MeasurePackage;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

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
	 * The cached value of the '{@link #getTerms() <em>Terms</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTerms()
	 * @generated
	 * @ordered
	 */
	protected EList<BaseMeasureTerm> terms;

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
	public EList<BaseMeasureTerm> getTerms() {
		if (terms == null) {
			terms = new EObjectContainmentEList<BaseMeasureTerm>(BaseMeasureTerm.class, this, MeasurePackage.DERIVED_MEASURE__TERMS);
		}
		return terms;
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
				return ((InternalEList<?>)getTerms()).basicRemove(otherEnd, msgs);
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
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MeasurePackage.DERIVED_MEASURE__TERMS:
				getTerms().clear();
				getTerms().addAll((Collection<? extends BaseMeasureTerm>)newValue);
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
				getTerms().clear();
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
				return terms != null && !terms.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //DerivedMeasureImpl
