public enum Roles {
    ADMIN(1),
    LECTURER(2),
    STUDENT(3);

    private int roleNumber;

    Roles(int roleNumber) {
        this.roleNumber = roleNumber;
    }

    public int getRole() {
        return roleNumber;
    }
}
