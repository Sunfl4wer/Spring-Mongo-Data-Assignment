package com.homework.superblog.model;

import java.util.EnumSet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Authorized {

  private EnumSet<Role> roles;
  private EnumSet<Permission> permissions;

  public Authorized (final EnumSet<Role> roles, final EnumSet<Permission> permissions) {
    this.roles = roles;
    this.permissions = permissions;
  }
}
