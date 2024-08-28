package Call_Center;

abstract class Employee {
	private Call currentCall = null;
	protected Rank rank;
	private CallHandler callHandler;
	
	public Employee(CallHandler handler) {
		callHandler = handler;
	}
	
	/* start conversation */
	public void recieveCall(Call call) {
		currentCall = call;
	}
	
	/* issue resolved, finish call */
	public void callCompleted() {
		if (currentCall != null) {
			/* disconnect the call */
			currentCall.disconnect();
			
			/* free the employee */
			currentCall = null;
		}
		
		/* Check if there is a call waiting in queue */
		assignNewCall();
	}
	
	/* issue not resolved, escalate the call */
	public void escalateAndReassign() {
		if (currentCall != null) {
			/* escalate call */
			currentCall.incrementRank();
			callHandler.dispatchCall(currentCall);
			
			/* free the employee */
			currentCall = null;
		}
		
		/* assign a new call */
		assignNewCall();
	}

	/* assign new call to employee, if free */
	private boolean assignNewCall() {
		if(!isFree()) {
			return false;
		}
		return callHandler.assignCall(this);
	}
	
	/* returns whether or not employee is free */
	public boolean isFree() {
		return currentCall == null;
	}
	
	public Rank getRank() {
		return rank;
	}
}
