/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.buck.apple;

import com.facebook.buck.core.rulekey.AddToRuleKey;
import com.facebook.buck.core.sourcepath.SourcePath;
import com.facebook.buck.core.util.immutables.BuckStyleValue;

/** File that are copied to specific subdirectory in bundle. */
@BuckStyleValue
public abstract class FileAppleBundlePart extends AppleBundlePart
    implements Comparable<FileAppleBundlePart> {

  @Override
  @AddToRuleKey
  public abstract SourcePath getSourcePath();

  @Override
  @AddToRuleKey
  public abstract AppleBundleDestination getDestination();

  @AddToRuleKey
  public abstract boolean getCodesignOnCopy();

  public static FileAppleBundlePart of(SourcePath sourcePath, AppleBundleDestination destination) {
    return of(sourcePath, destination, false);
  }

  public static FileAppleBundlePart of(
      SourcePath sourcePath, AppleBundleDestination destination, boolean codesignOnCopy) {
    return ImmutableFileAppleBundlePart.ofImpl(sourcePath, destination, codesignOnCopy);
  }

  @Override
  public int compareTo(FileAppleBundlePart o) {
    if (getCodesignOnCopy() != o.getCodesignOnCopy()) {
      return Boolean.compare(getCodesignOnCopy(), o.getCodesignOnCopy());
    }
    return super.compareTo(o);
  }
}
