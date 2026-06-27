/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package optimization;

public interface FinishingAlgorithm<TA extends FinishingAlgorithm<TA>> extends State<TA> {
    boolean isFinished();
}
