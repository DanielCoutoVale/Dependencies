package org.dependencies.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A condition for entering a system.
 * 
 * @author Daniel Couto-Vale
 */
public class DepEntryCondition {

	/**
	 * A general condition.
	 * 
	 * @author Daniel Couto-Vale
	 */
	private interface FeatureExpression {

		/**
		 * Gets whether this feature expression is fulfilled
		 * 
		 * @param featureNames the names of the unit features
		 * @return <code>true</code> if the unit fulfills this feature expression and
		 *         <code>false</code> otherwise.
		 */
		boolean isFulfilled(Set<String> featureNames);

		/**
		 * Gets whether the complement of this feature expression is fulfilled
		 * 
		 * @param featureNames the names of the unit features
		 * @return <code>true</code> if the unit fulfills the complement of this feature
		 *         expression and <code>false</code> otherwise.
		 */
		boolean hasComplementFulfilled(Set<String> featureNames);
	}

	/**
	 * An union expression.
	 * 
	 * @author Daniel Couto-Vale
	 */
	private static class UnionExpression implements FeatureExpression {

		/**
		 * The expressions of which at least one must be fulfilled.
		 */
		public final List<FeatureExpression> expressions = new LinkedList<>();

		@Override
		public final boolean isFulfilled(Set<String> featureNames) {
			boolean fulfilled = false;
			for (FeatureExpression condition : expressions) {
				fulfilled |= condition.isFulfilled(featureNames);
			}
			return fulfilled;
		}

		@Override
		public final boolean hasComplementFulfilled(Set<String> featureNames) {
			boolean fulfilled = true;
			for (FeatureExpression condition : expressions) {
				fulfilled &= condition.hasComplementFulfilled(featureNames);
			}
			return fulfilled;
		}

		@Override
		public final String toString() {
			StringBuffer buffer = new StringBuffer();
			for (FeatureExpression condition : expressions) {
				if (buffer.length() > 0) {
					buffer.append(" ∨ ");
				}
				buffer.append(condition.toString());
			}
			return buffer.toString();
		}

	}

	/**
	 * An intersection expression.
	 * 
	 * @author Daniel Couto-Vale
	 */
	private static class IntersectionExpression implements FeatureExpression {

		/**
		 * The expressions that must be fulfilled.
		 */
		public final List<FeatureExpression> expressions = new LinkedList<>();

		@Override
		public final boolean isFulfilled(Set<String> featureNames) {
			boolean fulfilled = true;
			for (FeatureExpression condition : expressions) {
				fulfilled &= condition.isFulfilled(featureNames);
			}
			return fulfilled;
		}

		@Override
		public final boolean hasComplementFulfilled(Set<String> featureNames) {
			boolean fulfilled = false;
			for (FeatureExpression condition : expressions) {
				fulfilled |= condition.hasComplementFulfilled(featureNames);
			}
			return fulfilled;
		}

		@Override
		public final String toString() {
			StringBuffer buffer = new StringBuffer();
			for (FeatureExpression condition : expressions) {
				if (buffer.length() > 0) {
					buffer.append(" ");
				}
				buffer.append(condition.toString());
			}
			return buffer.toString();
		}
	}

	/**
	 * A complement expression.
	 * 
	 * @author Daniel Couto-Vale
	 */
	private static class ComplementExpression implements FeatureExpression {

		/**
		 * The expression whose complement must be fulfilled.
		 */
		private final FeatureExpression expression;

		/**
		 * Constructor
		 * 
		 * @param expression the feature expression
		 */
		public ComplementExpression(FeatureExpression expression) {
			this.expression = expression;
		}

		@Override
		public final boolean isFulfilled(Set<String> featureNames) {
			return expression.hasComplementFulfilled(featureNames);
		}

		@Override
		public final boolean hasComplementFulfilled(Set<String> featureNames) {
			return expression.isFulfilled(featureNames);
		}

		@Override
		public final String toString() {
			return "-" + expression;
		}
	}

	/**
	 * A required feature.
	 * 
	 * @author Daniel Couto-Vale
	 */
	private static class RequiredFeature implements FeatureExpression {

		/**
		 * The feature name.
		 */
		private final String featureName;

		/**
		 * Constructor
		 * 
		 * @param featureName the feature name
		 */
		private RequiredFeature(String featureName) {
			this.featureName = featureName;
		}

		@Override
		public boolean isFulfilled(Set<String> featureNames) {
			return featureNames.contains(featureName);
		}

		@Override
		public boolean hasComplementFulfilled(Set<String> featureNames) {
			return featureNames.contains(featureName + "#Complement");
		}

		@Override
		public final String toString() {
			return featureName;
		}
	}

	/**
	 * The feature expression.
	 */
	private final FeatureExpression expression;

	/**
	 * Constructor
	 * 
	 * @param form the form
	 */
	public DepEntryCondition(String form) {
		this.expression = parse(form);
	}

	/**
	 * Parse simple expressions without parentheses.
	 * 
	 * @param form the form
	 * @return the expression
	 */
	private FeatureExpression parse(String form) {
		form = form.trim();
		UnionExpression or = new UnionExpression();
		for (String AND : form.split("∨")) {
			AND = AND.trim();
			IntersectionExpression and = new IntersectionExpression();
			for (String CHECK : AND.split(" ")) {
				CHECK = CHECK.trim();
				if (CHECK.length() == 0)
					continue;
				if (CHECK.startsWith("-")) {
					RequiredFeature check = new RequiredFeature(CHECK.substring(1));
					ComplementExpression not = new ComplementExpression(check);
					and.expressions.add(not);
				} else {
					RequiredFeature check = new RequiredFeature(CHECK);
					and.expressions.add(check);
				}
			}
			if (and.expressions.size() == 1) {
				or.expressions.add(and.expressions.get(0));
			} else {
				or.expressions.add(and);
			}
		}
		if (or.expressions.size() == 1) {
			return or.expressions.get(0);
		} else {
			return or;
		}
	}

	/**
	 * Gets whether the entry condition is fulfilled.
	 * 
	 * @param systems  the systems
	 * @param features the features
	 * @return <code>true</code> if this entry condition is fulfilled and
	 *         <code>false</code> otherwise.
	 */
	public final boolean isFulfilled(List<DepSystem> systems, List<DepFeature> features) {
		Set<String> featureNames = new HashSet<>();
		for (DepFeature feature : features) {
			for (DepSystem system : systems) {
				if (null != system.getFeature(feature.getName())) {
					for (DepFeature complement : system.getFeatures()) {
						if (feature.getName().equals(complement.getName())) {
							continue;
						}
						featureNames.add(complement.getName() + "#Complement");
					}
				}
			}
			featureNames.add(feature.getName());
		}
		return this.expression.isFulfilled(featureNames);
	}

	@Override
	public final String toString() {
		return this.expression.toString();
	}

}
