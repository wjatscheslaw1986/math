package algebra;

import java.util.List;

import static algebra.EquationUtil.isDistinct;

public final class EquationValidator {

    /**
     * Validates whether the given list of Member objects represents a quadratic or cubic equation.
     * Uses the distinct method to combine like terms and sort by power.
     *
     * @param equation the equation
     * @return EquationType indicating QUADRATIC, CUBIC, NEITHER, or INVALID
     */
    public static EquationType determineSingleVariableEquationType(Equation equation) {

        List<Member> members = equation.members();

        // Check for empty or null input
        if (members == null || members.isEmpty() || !isDistinct(members)) {
            return EquationType.INVALID;
        }

        // Verify all members have the same letter
        Letter referenceLetter = members.get(0).getLetter();
        boolean allSameLetter = members.stream().allMatch(m -> m.getLetter().equals(referenceLetter));
        if (!allSameLetter) {
            return EquationType.INVALID;
        }

        // Find the highest power with non-zero coefficient
        double maxPower = members.stream()
                .filter(m -> m.getCoefficient() != 0.0)
                .mapToDouble(Member::getPower)
                .max()
                .orElse(-1.0);

        // Check for non-integer or negative powers
        if (maxPower < 0 || maxPower != Math.floor(maxPower)) {
            return EquationType.INVALID;
        }

        // Use switch expression to determine equation type
        return switch ((int) maxPower) {
            case 2 -> EquationType.QUADRATIC;
            case 3 -> EquationType.CUBIC;
            default -> EquationType.NEITHER; // Other degrees
        };
    }
}
