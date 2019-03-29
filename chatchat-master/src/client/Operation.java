package client;

import java.util.ArrayList;

public class Operation extends WrappedObj{
	 public Operation(User user, int type) {
		 this.user = user;
		 operationType = type;
		 
	 }
	 public Operation(ArrayList<String> list) {
		 this.groupList = list;
		 operationType = Operation.BUILDGROUP;
	 }
	 public User getUser() {
		 return user;
	 }
	 public ArrayList<String> getList() {
		 return groupList;
	 }
	 
	 public int operationType;
	 public final static int ONLINE = 0;
	 public final static int ADDFRIEND = 1;
	 public final static int BUILDGROUP = 2;
	 private User user;
	 private ArrayList<String> groupList;
}
