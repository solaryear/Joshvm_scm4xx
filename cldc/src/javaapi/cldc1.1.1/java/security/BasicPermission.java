/*
 * @(#)BasicPermission.java	1.34 06/10/10
 *
 * Copyright  1990-2009 Sun Microsystems, Inc. All Rights Reserved.  
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER  
 *   
 * This program is free software; you can redistribute it and/or  
 * modify it under the terms of the GNU General Public License version  
 * 2 only, as published by the Free Software Foundation.  
 *   
 * This program is distributed in the hope that it will be useful, but  
 * WITHOUT ANY WARRANTY; without even the implied warranty of  
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  
 * General Public License version 2 for more details (a copy is  
 * included at /legal/license.txt).  
 *   
 * You should have received a copy of the GNU General Public License  
 * version 2 along with this work; if not, write to the Free Software  
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  
 * 02110-1301 USA  
 *   
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa  
 * Clara, CA 95054 or visit www.sun.com if you need additional  
 * information or have any questions.
 *
 */

package java.security;

import java.security.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.io.IOException;

/**
 * The BasicPermission class extends the Permission class, and
 * can be used as the base class for permissions that want to
 * follow the same naming convention as BasicPermission.
 * <P>
 * The name for a BasicPermission is the name of the given permission
 * (for example, "exit",
 * "setFactory", "print.queueJob", etc). The naming
 * convention follows the  hierarchical property naming convention.
 * An asterisk may appear by itself, or if immediately preceded by a "."
 * may appear at the end of the name, to signify a wildcard match.
 * For example, "*" and "java.*" are valid, while "*java", "a*b",
 * and "java*" are not valid.
 * <P>
 * The action string (inherited from Permission) is unused.
 * Thus, BasicPermission is commonly used as the base class for
 * "named" permissions
 * (ones that contain a name but no actions list; you either have the
 * named permission or you don't.)
 * Subclasses may implement actions on top of BasicPermission,
 * if desired.
 * <p>
 * <P>
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.security.PermissionCollection
 * @see java.lang.RuntimePermission
 * @see java.security.SecurityPermission
 * @see java.util.PropertyPermission
 * @see java.net.NetPermission
 * @see java.lang.SecurityManager
 *
 * @version 1.26 00/02/02
 *
 */

public abstract class BasicPermission extends Permission
{

    // does this permission have a wildcard at the end?
    private transient boolean wildcard;

    // the name without the wildcard on the end
    private transient String path;

    /**
     * initialize a BasicPermission object. Common to all constructors.
     *
     */

    private void init(String name)
    {
	if (name == null)
	    throw new NullPointerException("name can't be null");

	int len = name.length();
	    
	if (len == 0) {
	    throw new IllegalArgumentException("name can't be empty");
	}

	char last = name.charAt(len - 1);

	// Is wildcard or ends with ".*"?
	if (last == '*' && (len == 1 || name.charAt(len - 2) == '.')) {
	    wildcard = true;
	    if (len == 1) {
		path = "";
	    } else {
		path = name.substring(0, len - 1);
	    }
	} else {
	    path = name;
	}
    }

    /**
     * Creates a new BasicPermission with the specified name.
     * Name is the symbolic name of the permission, such as
     * "setFactory",
     * "print.queueJob", or "topLevelWindow", etc.
     *
     * @param name the name of the BasicPermission.
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty.
     */

    public BasicPermission(String name)
    {
	super(name);
	init(name);
    }


    /**
     * Creates a new BasicPermission object with the specified name.
     * The name is the symbolic name of the BasicPermission, and the
     * actions String is currently unused.
     *
     * @param name the name of the BasicPermission.
     * @param actions ignored.
     *
     * @throws NullPointerException if <code>name</code> is <code>null</code>.
     * @throws IllegalArgumentException if <code>name</code> is empty.
     */
    public BasicPermission(String name, String actions)
    {
	super(name);
	init(name);
    }

    /**
     * Checks if the specified permission is "implied" by
     * this object.
     * <P>
     * More specifically, this method returns true if:<p>
     * <ul>
     * <li> <i>p</i>'s class is the same as this object's class, and<p>
     * <li> <i>p</i>'s name equals or (in the case of wildcards)
     *      is implied by this object's
     *      name. For example, "a.b.*" implies "a.b.c".
     * </ul>
     *
     * @param p the permission to check against.
     *
     * @return true if the passed permission is equal to or
     * implied by this permission, false otherwise.
     */
    public boolean implies(Permission p) {
	if ((p == null) || (p.getClass() != getClass()))
	    return false;

	BasicPermission that = (BasicPermission) p;

	if (this.wildcard) {
	    if (that.wildcard)
		// one wildcard can imply another
		return that.path.startsWith(path);
	    else
		// make sure ap.path is longer so a.b.* doesn't imply a.b
		return (that.path.length() > this.path.length()) &&
		    that.path.startsWith(this.path);
	} else {
	    if (that.wildcard) {
		// a non-wildcard can't imply a wildcard
		return false;
	    }
	    else {
		return this.path.equals(that.path);
	    }
	}
    }

    /**
     * Checks two BasicPermission objects for equality.
     * Checks that <i>obj</i>'s class is the same as this object's class
     * and has the same name as this object.
     * <P>
     * @param obj the object we are testing for equality with this object.
     * @return true if <i>obj</i> is a BasicPermission, and has the same name
     *  as this BasicPermission object, false otherwise.
     */
    public boolean equals(Object obj) {
	if (obj == this)
	    return true;

	if ((obj == null) || (obj.getClass() != getClass()))
	    return false;

	BasicPermission bp = (BasicPermission) obj;

	return getName().equals(bp.getName());
    }


    /**
     * Returns the hash code value for this object.
     * The hash code used is the hash code of the name, that is,
     * <code>getName().hashCode()</code>, where <code>getName</code> is
     * from the Permission superclass.
     *
     * @return a hash code value for this object.
     */

    public int hashCode() {
	return this.getName().hashCode();
    }

    /**
     * Returns the canonical string representation of the actions,
     * which currently is the empty string "", since there are no actions for
     * a BasicPermission.
     *
     * @return the empty string "".
     */
    public String getActions()
    {
	return "";
    }

    /**
     * Returns a new PermissionCollection object for storing BasicPermission
     * objects.
     * <p>
     * A BasicPermissionCollection stores a collection of
     * BasicPermission permissions.
     *
     * <p>BasicPermission objects must be stored in a manner that allows them
     * to be inserted in any order, but that also enables the
     * PermissionCollection <code>implies</code> method
     * to be implemented in an efficient (and consistent) manner.
     *
     * @return a new PermissionCollection object suitable for
     * storing BasicPermissions.
     */

    public PermissionCollection newPermissionCollection() {
	return new BasicPermissionCollection(getClass());
    }

}

/**
 * A BasicPermissionCollection stores a collection
 * of BasicPermission permissions. BasicPermission objects
 * must be stored in a manner that allows them to be inserted in any
 * order, but enable the implies function to evaluate the implies
 * method in an efficient (and consistent) manner.
 *
 * A BasicPermissionCollection handles comparing a permission like "a.b.c.d.e"
 * with a Permission such as "a.b.*", or "*".
 *
 * @see java.security.Permission
 * @see java.security.Permissions
 * @see java.security.PermissionsImpl
 *
 * @version 1.26 02/02/00
 *
 *
 * @serial include
 */

final class BasicPermissionCollection
extends PermissionCollection
{
    private Hashtable perms;
    /**
     * This is set to <code>true</code> if this BasicPermissionCollection
     * contains a BasicPermission with '*' as its permission name.
     *
     * @see #serialPersistentFields
     */
    private boolean all_allowed; 

    /**
     * The class to which all BasicPermissions in this
     * BasicPermissionCollection belongs.
     *
     * @see #serialPersistentFields
     */
    private Class permClass;

    /**
     * Create an empty BasicPermissionCollection object.
     *
     */

    public BasicPermissionCollection(Class permClass) {
      this.permClass = permClass;
      perms = new Hashtable(11);
      all_allowed = false;
    }

    /**
     * Adds a permission to the BasicPermissions. The key for the hash is
     * permission.path.
     *
     * @param permission the Permission object to add.
     *
     * @exception IllegalArgumentException - if the permission is not a
     *                                       BasicPermission, or if
     *					     the permission is not of the
     *					     same Class as the other
     *					     permissions in this collection.
     *
     * @exception SecurityException - if this BasicPermissionCollection object
     *                                has been marked readonly
     */

    public void add(Permission permission)
    {
	if (! (permission instanceof BasicPermission))
	    throw new IllegalArgumentException("invalid permission: "+
					       permission);
	if (isReadOnly())
	    throw new SecurityException("attempt to add a Permission to a readonly PermissionCollection");

	BasicPermission bp = (BasicPermission) permission;

        // make sure we only add new BasicPermissions of the same class
	if (!permClass.isInstance(bp)) {
          throw new IllegalArgumentException("invalid permission: " +
	                                                       permission);
	}
        // No need to synchronize because all adds are done sequentially
	// before any implies() calls

	perms.put(bp.getName(), permission);
        if (!all_allowed) {
	    if (bp.getName().equals("*"))
		all_allowed = true;
	}
    }

    /**
     * Check and see if this set of permissions implies the permissions
     * expressed in "permission".
     *
     * @param p the Permission object to compare
     *
     * @return true if "permission" is a proper subset of a permission in
     * the set, false if not.
     */

    public boolean implies(Permission permission)
    {
	if (! (permission instanceof BasicPermission)) {
	  return false;
        }

	BasicPermission bp = (BasicPermission) permission;

	// random subclasses of BasicPermission do not imply each other
	if (!permClass.isInstance(bp)) {
	    return false;
        }

	// short circuit if the "*" Permission was added
	if (all_allowed) {
	    return true;
        }

	// strategy:
	// Check for full match first. Then work our way up the
	// path looking for matches on a.b..*

	String path_to_check = bp.getName();

	Permission x = (Permission) perms.get(path_to_check);

	if (x != null) {
	    // we have a direct hit!
	    return x.implies(permission);
	}

	// work our way up the tree...
	int last, offset;

	offset = path_to_check.length()-1;
//        throw new RuntimeException("to implement!");

	while ((last = path_to_check.lastIndexOf('.', offset)) != -1) {

	    path_to_check = path_to_check.substring(0, last+1) + "*";
	    //System.out.println("check "+path);
	    x = (Permission) perms.get(path_to_check);

	    if (x != null) {
		return x.implies(permission);
	    }
	    offset = last -1;
	}

	// we don't have to check for "*" as it was already checked
	// at the top (all_allowed), so we just return false
	return false;          
    }

    /**
     * Returns an enumeration of all the BasicPermission objects in the
     * container.
     *
     * @return an enumeration of all the BasicPermission objects.
     */

    public Enumeration elements() {
      return perms.elements();
    }
}
