/*
 * Viacheslav Mikhailov (taleskeeper@yandex.com) © 2026
 */

package optimization;

public interface State<S extends State<S>> {
    String BAD_STEP = "Wrong algorithm step for calling this method.";

    S next();
}
