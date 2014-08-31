package selectParser.visitor;

import java.util.Map;

public interface UDFFunction {
	Object invoke(Map<String, String> parameters) throws ParameterHasNoValueException,
			ParameterNotFoundException;
}
