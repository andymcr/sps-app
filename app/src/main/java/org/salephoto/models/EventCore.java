package org.salephoto.models;

import java.util.Date;


abstract public class EventCore {
	private int id;
	private boolean draft;
	private boolean cancelled;
	private String title;
	private Date startTime;
	private String duration;
	private Venue venue;

	public int getId() {
		return id;
	}

	public void setId(final int newId) {
		id = newId;
	}

	public boolean isDraft() {
		return draft;
	}

	public void setDraft(final boolean newDraft) {
		draft = newDraft;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(final boolean newCancelled) {
		cancelled = newCancelled;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String newTitle) {
		title = newTitle;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(final Date newStartTime) {
		startTime = newStartTime;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(final String newDuration) {
		duration = newDuration;
	}

	public Venue getVenue() {
		return venue;
	}

	public void setVenue(final Venue newVenue) {
		venue = newVenue;
	}

    @Override
    public String toString() {
        return getTitle();
    }

}
