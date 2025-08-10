package algebra;

import java.util.List;

public final class EquationValidator {

    /**
     * Validates whether the given list of Term objects represents a quadratic or cubic equation.
     * Uses the distinct method to combine like terms and sort by power.
     *
     * @param equation the equation
     * @return EquationType indicating LINEAR, QUADRATIC, CUBIC, QUARTIC, NEITHER, or INVALID
     */
    public static EquationType determinePolynomialEquationType(Equation equation) {

        List<Term> terms = equation.terms();

        // Check for empty or null input
        if (terms == null || terms.isEmpty()) {
            return EquationType.INVALID;
        }

        // Verify all terms have the same letter
        Letter referenceLetter = terms.get(0).getLetter();
        boolean allSameLetter = terms.stream().allMatch(m -> m.getLetter().equals(referenceLetter));
        if (!allSameLetter) {
            return EquationType.INVALID;
        }

        // Find the highest power with non-zero coefficient
        double maxPower = terms.stream()
                .filter(m -> m.getCoefficient() != 0.0)
                .mapToDouble(Term::getPower)
                .max()
                .orElse(-1.0);

        // Check for non-integer or negative powers
        if (maxPower < 0 || maxPower != Math.floor(maxPower)) {
            return EquationType.INVALID;
        }

        // Use switch expression to determine the equation type
        return switch ((int) maxPower) {
            case 1 -> EquationType.LINEAR;
            case 2 -> EquationType.QUADRATIC;
            case 3 -> EquationType.CUBIC;
            case 4 -> EquationType.QUARTIC;
            default -> EquationType.NEITHER; // Other max power degrees
        };
    }
}
