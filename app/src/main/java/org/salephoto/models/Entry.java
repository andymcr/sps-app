package org.salephoto.models;

import java.io.Serializable;

public class Entry {
	private int id;
	private String title;
	private Person author;
	private Image image;
	private Competition competition;
	private Section section;
	private int entryNumber;
	private int score;
	private int award;
	private int points;

	public int getId() {
		return id;
	}

	public void setId(final int newId) {
		id = newId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String newTitle) {
		title = newTitle;
	}

	public Person getAuthor() {
		return author;
	}

	public void setAuthor(final Person newAuthor) {
		author = newAuthor;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(final Image newImage) {
		image = newImage;
	}

	public Competition getCompetition() {
		return competition;
	}

	public void setCompetition(final Competition newCompetition) {
		competition = newCompetition;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(final Section newSection) {
		section = newSection;
	}

	public int getEntryNumber() {
		return entryNumber;
	}

	public void setEntryNumber(final int newEntryNumber) {
		entryNumber = newEntryNumber;
	}

	public int getScore() {
		return score;
	}

	public void setScore(final int newScore) {
		score = newScore;
	}

	public int getAward() {
		return award;
	}

	public void setAward(final int newAward) {
		award = newAward;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(final int newPoints) {
		points = newPoints;
	}

    @Override
    public String toString() {
        return getTitle();
    }

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Entry)) {
			return false;
		}

		return getId() == ((Entry) obj).getId();
	}

}
