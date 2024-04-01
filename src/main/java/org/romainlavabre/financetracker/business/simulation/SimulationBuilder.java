package org.romainlavabre.financetracker.business.simulation;

import org.romainlavabre.request.Request;

public interface SimulationBuilder {

    void getAverageYieldWithCurrentPortfolio();


    void getAverageYieldWithOverloadAmount( Request request );


}
