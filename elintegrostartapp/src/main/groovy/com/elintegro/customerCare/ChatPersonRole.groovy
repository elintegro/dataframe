package com.elintegro.customerCare

enum ChatPersonRole {

    SUPER_USER("SuperUser", "Super User"),
    TEAM_MEMBER("TeamMember", "Team Member"),
    SALES("Sales", "Sales"),
    ROLE_CUSTOMER("RoleCustomer", "Role Customer")

    private String role
    private String desc

    ChatPersonRole(String role, String desc) {
        this.role = role
        this.desc = desc
    }

    public String getRole() {
        return role
    }

    public String getDesc() {
        return desc
    }
}