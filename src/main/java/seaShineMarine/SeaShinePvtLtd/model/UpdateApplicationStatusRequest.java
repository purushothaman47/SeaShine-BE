package seaShineMarine.SeaShinePvtLtd.model;

/**
 * Body for PUT /api/v1/job-applications/{id}/status
 * e.g. { "status": "SHORTLISTED" }
 */
public record UpdateApplicationStatusRequest(String status) {
}
