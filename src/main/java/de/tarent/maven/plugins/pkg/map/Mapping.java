/*
 * Maven Packaging Plugin,
 * Maven plugin to package a Project (deb, ipk, izpack)
 * Copyright (C) 2000-2008 tarent GmbH
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License,version 2
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 *
 * tarent GmbH., hereby disclaims all copyright
 * interest in the program 'Maven Packaging Plugin'
 * Signature of Elmar Geese, 11 March 2008
 * Elmar Geese, CEO tarent GmbH.
 */

/**
 * 
 */
package de.tarent.maven.plugins.pkg.map;

import java.util.HashMap;

/**
 * A <code>Mapping</code> is the datatype that describes a target
 * distribution (id, label, packaging system, some path names)
 * and the important mapping betwen Maven2 artifacts and the
 * distribution's packages.
 * 
 * @author Robert Schuster (robert.schuster@tarent.de)
 *
 */
class Mapping
{
  String distro;
  
  String label;
  
  String parent;
  
  String packaging;
  
  String defaultBinPath;
  
  String defaultJarPath;

  String defaultJNIPath;
  
  String defaultDependencyLine;
  
  Boolean debianNaming;
  
  boolean hasNoPackages;
  
  HashMap/*<String, Entry>*/ entries = new HashMap();
  
  /**
   * Creates an empty mapping with the given distro name set.
   * 
   * @param distro
   */
  Mapping(String distro)
  {
    label = this.distro = distro;
  }
  
  /**
   * Creates a combination of the given child and parent mapping.
   * 
   * All properties from the child that are set are taken. The others
   * are taken from the parent.
   * 
   * In case of the mapping itself: The entries from the parent are
   * cloned and the ones from the child are added to it (possibly replacing
   * existing entries).
   * 
   * @param child
   * @param parent
   */
  Mapping (Mapping child, Mapping parent)
  {
    distro = child.distro;
    packaging = parent.packaging;
    
    // Whether packages exist or not is always inherit from the parent.
    hasNoPackages = parent.hasNoPackages;

    // These values may be null. If the merging has been done from the root to the child
    // they will be non-null for the parent however.
    debianNaming = (child.debianNaming != null) ? child.debianNaming : parent.debianNaming; 
    defaultJarPath = (child.defaultJarPath != null) ? child.defaultJarPath : parent.defaultJarPath; 
    defaultBinPath = (child.defaultBinPath != null) ? child.defaultBinPath : parent.defaultBinPath; 
    defaultJNIPath = (child.defaultJNIPath != null) ? child.defaultJNIPath : parent.defaultJNIPath; 
    defaultDependencyLine = (child.defaultDependencyLine != null) ? child.defaultDependencyLine : parent.defaultDependencyLine;
    
    entries = (HashMap) parent.entries.clone();
    entries.putAll(child.entries);
  }
  
  Entry getEntry(String groupId, String artifactId)
  {
    return (Entry) entries.get(groupId + ":" + artifactId);
  }
  
  void putEntry (String artifactSpec, Entry e)
  {
    entries.put(artifactSpec, e);
  }

}