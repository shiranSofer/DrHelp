package com.example.shiran.drhelp.entities.shifts;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.time.LocalTime;

@RequiresApi(api = Build.VERSION_CODES.O)
public enum ShiftTime {
    Morning(LocalTime.of(8,00), LocalTime.of(13,00)),
    Afternoon(LocalTime.of(13,00), LocalTime.of(18,00)),
    Evening(LocalTime.of(18,00),LocalTime.of(23,00));

    private LocalTime start;
    private LocalTime end;

    ShiftTime(LocalTime start, LocalTime end) {
        setStart(start);
        setEnd(end);
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}
