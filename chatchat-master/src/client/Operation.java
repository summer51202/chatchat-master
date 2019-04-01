package client;

import java.util.ArrayList;

public class Operation extends WrappedObj{
	 public Operation(User user) {
		 this.sender = user;
		 operationType = ONLINE; 
	 }
	 public Operation(User sender, User friend) {
		 this.sender = sender;
		 this.friend = friend;
		 operationType = ADDFRIEND;
	 }
	 public Operation(User sender,ArrayList<String> list) {
		 this.groupList = list;
		 this.sender = sender;
		 operationType = Operation.BUILDGROUP;
	 }
	 public User getSender() {
		 return sender;
	 }
	 public User getFriend() {
		 return friend;
	 }
	 public ArrayList<String> getList() {
		 return groupList;
	 }
	 
	 public int operationType;
	 public final static int ONLINE = 0;
	 public final static int ADDFRIEND = 1;
	 public final static int BUILDGROUP = 2;
	 private User sender, friend;
	 private ArrayList<String> groupList;
}
