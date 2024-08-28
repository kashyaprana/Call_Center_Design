package Call_Center;

import java.util.ArrayList;
import java.util.List;

public class CallHandler {
	/* 3 levels of employees: respondent, manager, director */
	private final int LEVELS = 3;
	
	/* Initialize with 10 respondents, 4 managers, and 2 directors */
	private final int NUM_RESPONDENTS = 10;
	private final int NUM_MANAGERS = 4;
	private final int NUM_DIRECTORS = 2;
	
	/* List of employee, by level,
	 * employeeLevels[0] = respondents
	 * employeeLevels[1] = managers
	 * employeeLevels[2] = directors
	 */
	List<List<Employee>> employeeLevels;
	
	/* queue for each call */
	List<List<Call>> callQueues;
	
	public CallHandler() {
		employeeLevels = new ArrayList<List<Employee>>(LEVELS);
		callQueues = new ArrayList<List<Call>>(LEVELS);
		
		// create respondents
		ArrayList<Employee> respondents = new ArrayList<Employee>(NUM_RESPONDENTS);
		for (int k = 0; k < NUM_RESPONDENTS - 1; k++) {
			respondents.add(new Respondent(this));
		}
		employeeLevels.add(respondents);
		
		// create managers
		ArrayList<Employee> managers = new ArrayList<Employee>(NUM_MANAGERS);
		for (int k = 0; k < NUM_MANAGERS - 1; k++) {
			managers.add(new Manager(this));
		}
		employeeLevels.add(managers);
		
		// create directors
		ArrayList<Employee> directors = new ArrayList<Employee>(NUM_DIRECTORS);
		for (int k = 0; k < NUM_DIRECTORS - 1; k++) {
			directors.add(new Respondent(this));
		}
		employeeLevels.add(directors);
		
	}
	
	/* get handler for call */
	public Employee getHandlerForCall(Call call) {
		for (int level = call.getRank().getValue(); level < LEVELS - 1; level++) {
			List<Employee> employeeLevel = employeeLevels.get(level);
			for (Employee emp : employeeLevel) {
				if (emp.isFree()) {
					return emp;
				}
			}
		}
		return null;
	}
	
	/* route call to available employee or save in queue */
	public void dispatchCall(Caller caller) {
		Call call = new Call(caller);
		dispatchCall(call);
	}
	
	/* route call to available employee or save in queue */
	public void dispatchCall(Call call) {
		/* try employee with minimal rank */
		Employee emp = getHandlerForCall(call);
		if (emp != null) {
			emp.recieveCall(call);
			call.setHandler(emp);
		} else {
			/* place call in queue */
			call.reply("Please wait for free employee to reply");
			callQueues.get(call.getRank().getValue()).add(call);
		}
	}
	
	/* employee free, check for waiting call */
	public boolean assignCall(Employee emp) {
		for (int rank = emp.getRank().getValue(); rank >= 0; rank--) {
			List<Call> que = callQueues.get(rank);
			
			if (que.size() > 0) {
				Call call = que.remove(0);
				if (call != null) {
					emp.recieveCall(call);
					return true;
				}
			}
		}
		return false;
	}
	
	
	
}
