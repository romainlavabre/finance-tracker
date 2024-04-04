package org.romainlavabre.financetracker.configuration.json.put;

import org.romainlavabre.encoder.put.Put;
import org.romainlavabre.financetracker.entity.SectorDistribution;
import org.springframework.stereotype.Service;

@Service
public class PutSectorName implements Put< SectorDistribution > {

    @Override
    public Object build( SectorDistribution sectorDistribution ) {
        return sectorDistribution.getSector().getName();
    }
}
