/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.core.server.notifier;

import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import cc.kune.wave.server.kspecific.WaveEmailNotifier;

import com.google.inject.Inject;

public class ClearUpdatedWavesHourlyJob implements Job {

  public static final Log LOG = LogFactory.getLog(ClearUpdatedWavesHourlyJob.class);

  private final WaveEmailNotifier waveNotifier;

  @Inject
  public ClearUpdatedWavesHourlyJob(final WaveEmailNotifier waveNotifier) throws ParseException,
      SchedulerException {
    this.waveNotifier = waveNotifier;
  }

  @Override
  public void execute(final JobExecutionContext context) throws JobExecutionException {
    waveNotifier.clearUpdatedWaves();
  }

}
