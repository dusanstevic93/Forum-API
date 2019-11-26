package com.dusan.forum.swagger;

public final class OperationDescription {

	private OperationDescription() {

	}

	public static final String CREATE_FORUM = "Creates a new forum. Only authenticated users with ADMIN role "
			+ "can perform this operation.";

	public static final String GET_FORUMS = "Returns a list of forums. By specifying parameter 'root',"
			+ " this operation can return all forums, or only root forums.";

	public static final String GET_FORUM = "Returns the single forum identified by id.";

	public static final String GET_SUB_FORUMS = "Returns a list of sub forums of the forum identified by id.";

	public static final String CREATE_TOPIC = "Creates a new topic on the forum identified by id. "
			+ "Only authenticated users with MEMBER role can perform this operation.";

	public static final String GET_TOPIC = "Returns the single topic identified by id.";

	public static final String GET_FORUM_TOPICS = "Returns a list of topics created on the forum identified by id.";

	public static final String GET_USER_TOPICS = "Returns a list of topics created by the user identified by id.";

	public static final String DELETE_TOPIC = "Deletes the single topic identified by id. "
			+ "Only authenticated users with ADMIN role can perform this operation.";

	public static final String CREATE_POST = "Creates a new post on specific topic identified by id. "
			+ "Only authenticated users with MEMBER role can perform this operation.";

	public static final String GET_POST = "Returns the single post identified by id";

	public static final String GET_SUB_POSTS = "Returns a list of sub posts of the post identified by id.";

	public static final String GET_TOPIC_POSTS = "Returns a list of posts created on the topic identified by id.";

	public static final String GET_USER_POSTS = "Returns a list of posts created by the user identified by id.";

	public static final String EDIT_POST = "Edits the post identified by id. Only authenticated users with MEMBER role can perform "
			+ "this operation. User can edit only own posts.";

	public static final String DELETE_POST = "Deletes the single post identified by id. Only authenticated users can perform this operation. "
			+ "Authenticated users with ADMIN role can delete any post, while authenticated users with MEMBER role can "
			+ "delete only own posts.";

	public static final String CRATE_USER = "Creates a new user. Created user has MEMBER role.";

	public static final String GET_USER = "Returns the single user identified by id.";

	public static final String GET_USERS = "Returns a list of all users.";
}
