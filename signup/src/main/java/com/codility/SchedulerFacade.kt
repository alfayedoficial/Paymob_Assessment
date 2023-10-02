package com.codility

import io.reactivex.Scheduler

interface SchedulerFacade {
     val main: Scheduler
     val background: Scheduler
}