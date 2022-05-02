package model;

import java.time.Month;

/**
 * Model for a row of data in the Total Appointments by Type report. Each data member corresponds to a column value in the report table that is generated. <br><br>
 * @see reporting.TotalAppointmentsByTypeReport
 */
public class AppointmentReportItem {
    private String type;
    private int january = 0;
    private int february = 0;
    private int march = 0;
    private int april = 0;
    private int may = 0;
    private int june = 0;
    private int july = 0;
    private int august = 0;
    private int september = 0;
    private int october = 0;
    private int november = 0;
    private int december = 0;

    /**
     * Class constructor. Sets the Appointment type.<br><br>
     * @param type the appointment type (string)
     */
    public AppointmentReportItem(String type){
        this.type = type;
    }

    /**
     * Gets the Appointment type.<br><br>
     * @return the Appointment type for the row
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the appointment type.<br><br>
     * @param type the appointment type for the row
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Increments the Month data member value matching the parameter provided.<br><br>
     * @param month the Month object to increment
     * @see java.time.Month
     */
    public void incrementMonth(Month month) {
        switch (month) {
            case JANUARY: this.january += 1; break;
            case FEBRUARY: this.february += 1; break;
            case MARCH: this.march += 1; break;
            case MAY: this.may += 1; break;
            case JUNE: this.june += 1; break;
            case JULY: this.july += 1; break;
            case AUGUST: this.august += 1; break;
            case SEPTEMBER: this.september += 1; break;
            case OCTOBER: this.october += 1; break;
            case NOVEMBER: this.november += 1; break;
            case DECEMBER: this.december += 1; break;
        }
    }

    /**
     * Gets the value for the month of January<br><br>
     * @return the number of appointments in January
     */
    public int getJanuary() {
        return january;
    }
    /**
     * Sets the value for the month of January<br><br>
     * @param january the number of appointments in January
     */
    public void setJanuary(int january) {
        this.january = january;
    }
    /**
     * Gets the value for the month of February<br><br>
     * @return the number of appointments in February
     */
    public int getFebruary() {
        return february;
    }
    /**
     * Sets the value for the month of February<br><br>
     * @param february the number of appointments in February
     */
    public void setFebruary(int february) {
        this.february = february;
    }
    /**
     * Gets the value for the month of March<br><br>
     * @return the number of appointments in March
     */
    public int getMarch() {
        return march;
    }
    /**
     * Sets the value for the month of March<br><br>
     * @param march the number of appointments in March
     */
    public void setMarch(int march) {
        this.march = march;
    }
    /**
     * Gets the value for the month of April<br><br>
     * @return the number of appointments in April
     */
    public int getApril() {
        return april;
    }
    /**
     * Sets the value for the month of April<br><br>
     * @param april the number of appointments in April
     */
    public void setApril(int april) {
        this.april = april;
    }
    /**
     * Gets the value for the month of May<br><br>
     * @return the number of appointments in May
     */
    public int getMay() {
        return may;
    }
    /**
     * Sets the value for the month of May<br><br>
     * @param may the number of appointments in May
     */
    public void setMay(int may) {
        this.may = may;
    }
    /**
     * Gets the value for the month of June<br><br>
     * @return the number of appointments in June
     */
    public int getJune() {
        return june;
    }
    /**
     * Sets the value for the month of June<br><br>
     * @param june the number of appointments in June
     */
    public void setJune(int june) {
        this.june = june;
    }
    /**
     * Gets the value for the month of July<br><br>
     * @return the number of appointments in July
     */
    public int getJuly() {
        return july;
    }
    /**
     * Sets the value for the month of July<br><br>
     * @param july the number of appointments in July
     */
    public void setJuly(int july) {
        this.july = july;
    }
    /**
     * Gets the value for the month of August<br><br>
     * @return the number of appointments in August
     */
    public int getAugust() {
        return august;
    }
    /**
     * Sets the value for the month of August<br><br>
     * @param august the number of appointments in August
     */
    public void setAugust(int august) {
        this.august = august;
    }
    /**
     * Gets the value for the month of September<br><br>
     * @return the number of appointments in September
     */
    public int getSeptember() {
        return september;
    }
    /**
     * Sets the value for the month of September<br><br>
     * @param september the number of appointments in September
     */
    public void setSeptember(int september) {
        this.september = september;
    }
    /**
     * Gets the value for the month of October<br><br>
     * @return the number of appointments in October
     */
    public int getOctober() {
        return october;
    }
    /**
     * Sets the value for the month of October<br><br>
     * @param october the number of appointments in October
     */
    public void setOctober(int october) {
        this.october = october;
    }
    /**
     * Gets the value for the month of November<br><br>
     * @return the number of appointments in November
     */
    public int getNovember() {
        return november;
    }
    /**
     * Sets the value for the month of November<br><br>
     * @param november the number of appointments in November
     */
    public void setNovember(int november) {
        this.november = november;
    }
    /**
     * Gets the value for the month of December<br><br>
     * @return the number of appointments in December
     */
    public int getDecember() {
        return december;
    }
    /**
     * Sets the value for the month of December<br><br>
     * @param december the number of appointments in December
     */
    public void setDecember(int december) {
        this.december = december;
    }
}
