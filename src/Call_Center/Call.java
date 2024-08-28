package Call_Center;

public class Call {
	/* minimal rank of employee who can handle this call */
	private Rank rank;
	
	/* person calling */
	private Caller caller;
	
	/* employee handling call */
	private Employee handler;
	
	public Call(Caller c) {
		rank = Rank.Responder;
		caller = c;
	}
	
	/* set employee to handle call */
	public void setHandler(Employee e) {
		handler = e;
	}
	
	/* play recorded message to customer */
	public void reply(String message) {
		System.out.println(message);
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public void setRank(Rank r) {
		rank = r;
	}
	
	public Rank incrementRank() {
		if (rank == Rank.Responder) {
			rank = Rank.Manager;
		} else if (rank == Rank.Manager) {
			rank = Rank.Director;
		}
		return rank;
	}
	
	/* disconnect call */
	public void disconnect() {
		reply("Thank you for calling");
	}
}
