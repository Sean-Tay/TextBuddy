import java.util.ArrayList;
import java.util.List;

public class TBFnOutput {
	
	private List<String> storList;
	private boolean success;
	
	public TBFnOutput () {
		this.storList = new ArrayList<String>();
		this.success = false;
	}
	
	public TBFnOutput (boolean success) {
		this.storList = new ArrayList<String>();
		this.success = success;
	}
	
	public TBFnOutput (List<String> list) {
		this.storList = list;
		this.success = false;
	}
	
	public TBFnOutput (List<String> list, boolean success) {
		this.storList = list;
		this.success = success;
	}
	
	public List<String> getList () {
		
		return this.storList;
	}
	
	public boolean getSuccess () {
		
		return this.success;
	}
	
	public void setList (List<String> newList) {
		
		this.storList = newList;
	}
	
	public void setBoolean (boolean success) {
		
		this.success = success;
	}
	
	
}
