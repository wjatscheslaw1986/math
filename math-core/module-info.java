module math {
	exports linear.equation;
	exports linear.spatial.exception;
	exports linear.matrix.exception;
	exports linear.equation.exception;
	exports linear.matrix;
	exports linear.spatial;
//	opens linear.equation to testing;
//	opens linear.spatial.exception to testing;
//	opens linear.matrix.exception to testing;
//	opens linear.equation.exception to testing;
//	opens linear.matrix to testing;
//	opens linear.spatial to testing;
	requires org.junit.jupiter.api;
//	opens test to org.junit.platform.commons;
}