package com.ross53.cobar.EntityWrapper;

import com.ross53.cobar.domain.PositionInfo;

import java.util.ArrayList;
import java.util.List;

public class PositionWrapper {

   private List<PositionInfo> positions = new ArrayList<PositionInfo>();

    public List<PositionInfo> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionInfo> positions) {
        this.positions = positions;
    }
}
