package com.example.mygarden.model.enums;

import java.util.Collections;
import java.util.Set;

import static com.example.mygarden.model.enums.PermissionEnum.*;

public enum RoleEnum {

    USER(Collections.emptySet()),
    ADMIN(
            Set.of( ADMIN_READ, ADMIN_CREATE, ADMIN_UPDATE, ADMIN_DELETE,
                    MODERATOR_READ, MODERATOR_CREATE, MODERATOR_UPDATE, MODERATOR_DELETE)
    ),
    MODERATOR(
            Set.of(MODERATOR_READ, MODERATOR_CREATE, MODERATOR_UPDATE, MODERATOR_DELETE)
    );


    private Set<PermissionEnum> permissions;

    RoleEnum(Set<PermissionEnum> permissions) {
        this.permissions = permissions;
    }

    public void setPermissions(Set<PermissionEnum> permissions) {
        this.permissions = permissions;
    }

    public Set<PermissionEnum> getPermissions() {
        return permissions;
    }
}
