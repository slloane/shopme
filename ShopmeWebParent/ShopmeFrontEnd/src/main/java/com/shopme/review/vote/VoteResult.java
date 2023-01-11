package com.shopme.review.vote;

public class VoteResult {
	private boolean successful;
	private String message;
	private int voteCount;

	public static VoteResult fail(String message) {
		return new VoteResult(false, message, 0);
	}

	public static VoteResult success(String message, int voteCount) {
		return new VoteResult(true, message, voteCount);
	}
	
	private VoteResult(boolean successful, String message, int voteCount) {
		this.successful = successful;
		this.message = message;
		this.voteCount = voteCount;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

}
