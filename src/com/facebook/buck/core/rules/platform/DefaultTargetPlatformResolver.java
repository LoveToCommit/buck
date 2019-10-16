/*
 * Copyright 2019-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.facebook.buck.core.rules.platform;

import com.facebook.buck.core.exceptions.DependencyStack;
import com.facebook.buck.core.exceptions.HumanReadableException;
import com.facebook.buck.core.model.ConfigurationForConfigurationTargets;
import com.facebook.buck.core.model.TargetConfiguration;
import com.facebook.buck.core.model.UnconfiguredTargetConfiguration;
import com.facebook.buck.core.model.impl.RuleBasedTargetConfiguration;
import com.facebook.buck.core.model.platform.Platform;
import com.facebook.buck.core.model.platform.TargetPlatformResolver;
import com.facebook.buck.core.model.platform.impl.UnconfiguredPlatform;

/**
 * {@link TargetPlatformResolver} that supports both rule based platforms and a platform for an
 * empty target configuration.
 */
public class DefaultTargetPlatformResolver implements TargetPlatformResolver {

  private final RuleBasedTargetPlatformResolver ruleBasedTargetPlatformResolver;

  public DefaultTargetPlatformResolver(
      RuleBasedTargetPlatformResolver ruleBasedTargetPlatformResolver) {
    this.ruleBasedTargetPlatformResolver = ruleBasedTargetPlatformResolver;
  }

  @Override
  public Platform getTargetPlatform(
      TargetConfiguration targetConfiguration, DependencyStack dependencyStack) {
    if (targetConfiguration instanceof UnconfiguredTargetConfiguration
        || targetConfiguration instanceof ConfigurationForConfigurationTargets) {
      return UnconfiguredPlatform.INSTANCE;
    } else if (targetConfiguration instanceof RuleBasedTargetConfiguration) {
      return ruleBasedTargetPlatformResolver.getTargetPlatform(
          targetConfiguration, dependencyStack);
    }
    throw new HumanReadableException(
        "Cannot determine target platform for configuration: " + targetConfiguration);
  }
}
