/**
 */
package hu.blackbelt.judo.meta.measure;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see hu.blackbelt.judo.meta.measure.MeasureFactory
 * @model kind="package"
 * @generated
 */
public interface MeasurePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "measure";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://blackbelt.hu/judo/asm/measure";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "measure";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MeasurePackage eINSTANCE = hu.blackbelt.judo.meta.measure.impl.MeasurePackageImpl.init();

	/**
	 * The meta object id for the '{@link hu.blackbelt.judo.meta.measure.impl.MeasureImpl <em>Measure</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see hu.blackbelt.judo.meta.measure.impl.MeasureImpl
	 * @see hu.blackbelt.judo.meta.measure.impl.MeasurePackageImpl#getMeasure()
	 * @generated
	 */
	int MEASURE = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASURE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Rate Dividend</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASURE__RATE_DIVIDEND = 1;

	/**
	 * The feature id for the '<em><b>Rate Divisor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASURE__RATE_DIVISOR = 2;

	/**
	 * The feature id for the '<em><b>Units</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASURE__UNITS = 3;

	/**
	 * The number of structural features of the '<em>Measure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASURE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Measure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEASURE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link hu.blackbelt.judo.meta.measure.impl.UnitImpl <em>Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see hu.blackbelt.judo.meta.measure.impl.UnitImpl
	 * @see hu.blackbelt.judo.meta.measure.impl.MeasurePackageImpl#getUnit()
	 * @generated
	 */
	int UNIT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT__NAME = 0;

	/**
	 * The number of structural features of the '<em>Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link hu.blackbelt.judo.meta.measure.impl.DerivedMeasureImpl <em>Derived Measure</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see hu.blackbelt.judo.meta.measure.impl.DerivedMeasureImpl
	 * @see hu.blackbelt.judo.meta.measure.impl.MeasurePackageImpl#getDerivedMeasure()
	 * @generated
	 */
	int DERIVED_MEASURE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_MEASURE__NAME = MEASURE__NAME;

	/**
	 * The feature id for the '<em><b>Rate Dividend</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_MEASURE__RATE_DIVIDEND = MEASURE__RATE_DIVIDEND;

	/**
	 * The feature id for the '<em><b>Rate Divisor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_MEASURE__RATE_DIVISOR = MEASURE__RATE_DIVISOR;

	/**
	 * The feature id for the '<em><b>Units</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_MEASURE__UNITS = MEASURE__UNITS;

	/**
	 * The feature id for the '<em><b>Terms</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_MEASURE__TERMS = MEASURE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Derived Measure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_MEASURE_FEATURE_COUNT = MEASURE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Derived Measure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DERIVED_MEASURE_OPERATION_COUNT = MEASURE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link hu.blackbelt.judo.meta.measure.impl.BaseMeasureImpl <em>Base Measure</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see hu.blackbelt.judo.meta.measure.impl.BaseMeasureImpl
	 * @see hu.blackbelt.judo.meta.measure.impl.MeasurePackageImpl#getBaseMeasure()
	 * @generated
	 */
	int BASE_MEASURE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MEASURE__NAME = MEASURE__NAME;

	/**
	 * The feature id for the '<em><b>Rate Dividend</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MEASURE__RATE_DIVIDEND = MEASURE__RATE_DIVIDEND;

	/**
	 * The feature id for the '<em><b>Rate Divisor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MEASURE__RATE_DIVISOR = MEASURE__RATE_DIVISOR;

	/**
	 * The feature id for the '<em><b>Units</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MEASURE__UNITS = MEASURE__UNITS;

	/**
	 * The number of structural features of the '<em>Base Measure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MEASURE_FEATURE_COUNT = MEASURE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Base Measure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MEASURE_OPERATION_COUNT = MEASURE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link hu.blackbelt.judo.meta.measure.impl.BaseMeasureTermImpl <em>Base Measure Term</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see hu.blackbelt.judo.meta.measure.impl.BaseMeasureTermImpl
	 * @see hu.blackbelt.judo.meta.measure.impl.MeasurePackageImpl#getBaseMeasureTerm()
	 * @generated
	 */
	int BASE_MEASURE_TERM = 4;

	/**
	 * The feature id for the '<em><b>Exponent</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MEASURE_TERM__EXPONENT = 0;

	/**
	 * The feature id for the '<em><b>Base Measure</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MEASURE_TERM__BASE_MEASURE = 1;

	/**
	 * The number of structural features of the '<em>Base Measure Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MEASURE_TERM_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Base Measure Term</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MEASURE_TERM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link hu.blackbelt.judo.meta.measure.impl.DurationUnitImpl <em>Duration Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see hu.blackbelt.judo.meta.measure.impl.DurationUnitImpl
	 * @see hu.blackbelt.judo.meta.measure.impl.MeasurePackageImpl#getDurationUnit()
	 * @generated
	 */
	int DURATION_UNIT = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_UNIT__NAME = UNIT__NAME;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_UNIT__TYPE = UNIT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Duration Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_UNIT_FEATURE_COUNT = UNIT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Duration Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_UNIT_OPERATION_COUNT = UNIT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link hu.blackbelt.judo.meta.measure.DurationType <em>Duration Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see hu.blackbelt.judo.meta.measure.DurationType
	 * @see hu.blackbelt.judo.meta.measure.impl.MeasurePackageImpl#getDurationType()
	 * @generated
	 */
	int DURATION_TYPE = 6;


	/**
	 * Returns the meta object for class '{@link hu.blackbelt.judo.meta.measure.Measure <em>Measure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Measure</em>'.
	 * @see hu.blackbelt.judo.meta.measure.Measure
	 * @generated
	 */
	EClass getMeasure();

	/**
	 * Returns the meta object for the attribute '{@link hu.blackbelt.judo.meta.measure.Measure#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see hu.blackbelt.judo.meta.measure.Measure#getName()
	 * @see #getMeasure()
	 * @generated
	 */
	EAttribute getMeasure_Name();

	/**
	 * Returns the meta object for the attribute '{@link hu.blackbelt.judo.meta.measure.Measure#getRateDividend <em>Rate Dividend</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rate Dividend</em>'.
	 * @see hu.blackbelt.judo.meta.measure.Measure#getRateDividend()
	 * @see #getMeasure()
	 * @generated
	 */
	EAttribute getMeasure_RateDividend();

	/**
	 * Returns the meta object for the attribute '{@link hu.blackbelt.judo.meta.measure.Measure#getRateDivisor <em>Rate Divisor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rate Divisor</em>'.
	 * @see hu.blackbelt.judo.meta.measure.Measure#getRateDivisor()
	 * @see #getMeasure()
	 * @generated
	 */
	EAttribute getMeasure_RateDivisor();

	/**
	 * Returns the meta object for the reference list '{@link hu.blackbelt.judo.meta.measure.Measure#getUnits <em>Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Units</em>'.
	 * @see hu.blackbelt.judo.meta.measure.Measure#getUnits()
	 * @see #getMeasure()
	 * @generated
	 */
	EReference getMeasure_Units();

	/**
	 * Returns the meta object for class '{@link hu.blackbelt.judo.meta.measure.Unit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unit</em>'.
	 * @see hu.blackbelt.judo.meta.measure.Unit
	 * @generated
	 */
	EClass getUnit();

	/**
	 * Returns the meta object for the attribute '{@link hu.blackbelt.judo.meta.measure.Unit#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see hu.blackbelt.judo.meta.measure.Unit#getName()
	 * @see #getUnit()
	 * @generated
	 */
	EAttribute getUnit_Name();

	/**
	 * Returns the meta object for class '{@link hu.blackbelt.judo.meta.measure.DerivedMeasure <em>Derived Measure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Derived Measure</em>'.
	 * @see hu.blackbelt.judo.meta.measure.DerivedMeasure
	 * @generated
	 */
	EClass getDerivedMeasure();

	/**
	 * Returns the meta object for the containment reference '{@link hu.blackbelt.judo.meta.measure.DerivedMeasure#getTerms <em>Terms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Terms</em>'.
	 * @see hu.blackbelt.judo.meta.measure.DerivedMeasure#getTerms()
	 * @see #getDerivedMeasure()
	 * @generated
	 */
	EReference getDerivedMeasure_Terms();

	/**
	 * Returns the meta object for class '{@link hu.blackbelt.judo.meta.measure.BaseMeasure <em>Base Measure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Base Measure</em>'.
	 * @see hu.blackbelt.judo.meta.measure.BaseMeasure
	 * @generated
	 */
	EClass getBaseMeasure();

	/**
	 * Returns the meta object for class '{@link hu.blackbelt.judo.meta.measure.BaseMeasureTerm <em>Base Measure Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Base Measure Term</em>'.
	 * @see hu.blackbelt.judo.meta.measure.BaseMeasureTerm
	 * @generated
	 */
	EClass getBaseMeasureTerm();

	/**
	 * Returns the meta object for the attribute '{@link hu.blackbelt.judo.meta.measure.BaseMeasureTerm#getExponent <em>Exponent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Exponent</em>'.
	 * @see hu.blackbelt.judo.meta.measure.BaseMeasureTerm#getExponent()
	 * @see #getBaseMeasureTerm()
	 * @generated
	 */
	EAttribute getBaseMeasureTerm_Exponent();

	/**
	 * Returns the meta object for the reference '{@link hu.blackbelt.judo.meta.measure.BaseMeasureTerm#getBaseMeasure <em>Base Measure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Base Measure</em>'.
	 * @see hu.blackbelt.judo.meta.measure.BaseMeasureTerm#getBaseMeasure()
	 * @see #getBaseMeasureTerm()
	 * @generated
	 */
	EReference getBaseMeasureTerm_BaseMeasure();

	/**
	 * Returns the meta object for class '{@link hu.blackbelt.judo.meta.measure.DurationUnit <em>Duration Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Duration Unit</em>'.
	 * @see hu.blackbelt.judo.meta.measure.DurationUnit
	 * @generated
	 */
	EClass getDurationUnit();

	/**
	 * Returns the meta object for the attribute '{@link hu.blackbelt.judo.meta.measure.DurationUnit#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see hu.blackbelt.judo.meta.measure.DurationUnit#getType()
	 * @see #getDurationUnit()
	 * @generated
	 */
	EAttribute getDurationUnit_Type();

	/**
	 * Returns the meta object for enum '{@link hu.blackbelt.judo.meta.measure.DurationType <em>Duration Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Duration Type</em>'.
	 * @see hu.blackbelt.judo.meta.measure.DurationType
	 * @generated
	 */
	EEnum getDurationType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MeasureFactory getMeasureFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link hu.blackbelt.judo.meta.measure.impl.MeasureImpl <em>Measure</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see hu.blackbelt.judo.meta.measure.impl.MeasureImpl
		 * @see hu.blackbelt.judo.meta.measure.impl.MeasurePackageImpl#getMeasure()
		 * @generated
		 */
		EClass MEASURE = eINSTANCE.getMeasure();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEASURE__NAME = eINSTANCE.getMeasure_Name();

		/**
		 * The meta object literal for the '<em><b>Rate Dividend</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEASURE__RATE_DIVIDEND = eINSTANCE.getMeasure_RateDividend();

		/**
		 * The meta object literal for the '<em><b>Rate Divisor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEASURE__RATE_DIVISOR = eINSTANCE.getMeasure_RateDivisor();

		/**
		 * The meta object literal for the '<em><b>Units</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MEASURE__UNITS = eINSTANCE.getMeasure_Units();

		/**
		 * The meta object literal for the '{@link hu.blackbelt.judo.meta.measure.impl.UnitImpl <em>Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see hu.blackbelt.judo.meta.measure.impl.UnitImpl
		 * @see hu.blackbelt.judo.meta.measure.impl.MeasurePackageImpl#getUnit()
		 * @generated
		 */
		EClass UNIT = eINSTANCE.getUnit();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNIT__NAME = eINSTANCE.getUnit_Name();

		/**
		 * The meta object literal for the '{@link hu.blackbelt.judo.meta.measure.impl.DerivedMeasureImpl <em>Derived Measure</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see hu.blackbelt.judo.meta.measure.impl.DerivedMeasureImpl
		 * @see hu.blackbelt.judo.meta.measure.impl.MeasurePackageImpl#getDerivedMeasure()
		 * @generated
		 */
		EClass DERIVED_MEASURE = eINSTANCE.getDerivedMeasure();

		/**
		 * The meta object literal for the '<em><b>Terms</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DERIVED_MEASURE__TERMS = eINSTANCE.getDerivedMeasure_Terms();

		/**
		 * The meta object literal for the '{@link hu.blackbelt.judo.meta.measure.impl.BaseMeasureImpl <em>Base Measure</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see hu.blackbelt.judo.meta.measure.impl.BaseMeasureImpl
		 * @see hu.blackbelt.judo.meta.measure.impl.MeasurePackageImpl#getBaseMeasure()
		 * @generated
		 */
		EClass BASE_MEASURE = eINSTANCE.getBaseMeasure();

		/**
		 * The meta object literal for the '{@link hu.blackbelt.judo.meta.measure.impl.BaseMeasureTermImpl <em>Base Measure Term</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see hu.blackbelt.judo.meta.measure.impl.BaseMeasureTermImpl
		 * @see hu.blackbelt.judo.meta.measure.impl.MeasurePackageImpl#getBaseMeasureTerm()
		 * @generated
		 */
		EClass BASE_MEASURE_TERM = eINSTANCE.getBaseMeasureTerm();

		/**
		 * The meta object literal for the '<em><b>Exponent</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BASE_MEASURE_TERM__EXPONENT = eINSTANCE.getBaseMeasureTerm_Exponent();

		/**
		 * The meta object literal for the '<em><b>Base Measure</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BASE_MEASURE_TERM__BASE_MEASURE = eINSTANCE.getBaseMeasureTerm_BaseMeasure();

		/**
		 * The meta object literal for the '{@link hu.blackbelt.judo.meta.measure.impl.DurationUnitImpl <em>Duration Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see hu.blackbelt.judo.meta.measure.impl.DurationUnitImpl
		 * @see hu.blackbelt.judo.meta.measure.impl.MeasurePackageImpl#getDurationUnit()
		 * @generated
		 */
		EClass DURATION_UNIT = eINSTANCE.getDurationUnit();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DURATION_UNIT__TYPE = eINSTANCE.getDurationUnit_Type();

		/**
		 * The meta object literal for the '{@link hu.blackbelt.judo.meta.measure.DurationType <em>Duration Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see hu.blackbelt.judo.meta.measure.DurationType
		 * @see hu.blackbelt.judo.meta.measure.impl.MeasurePackageImpl#getDurationType()
		 * @generated
		 */
		EEnum DURATION_TYPE = eINSTANCE.getDurationType();

	}

} //MeasurePackage
