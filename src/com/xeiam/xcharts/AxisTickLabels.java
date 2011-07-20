/**
 * Copyright 2011 Xeiam LLC.
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
package com.xeiam.xcharts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

import com.xeiam.xcharts.interfaces.IChartPart;

/**
 * Axis tick labels
 */
public class AxisTickLabels implements IChartPart {

    /** the axis */
    private Axis axis;

    private AxisTick axisTick;

    /** the font */
    private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 12); // default font

    /** the foreground color */
    private Color foreground = ChartColor.getAWTColor(ChartColor.DARK_GREY);// default foreground color

    /** the bounds */
    private Rectangle bounds = new Rectangle(); // default all-zero rectangle

    /**
     * Constructor
     * 
     * @param axis the axis
     */
    protected AxisTickLabels(Axis axis, AxisTick axisTick) {

        this.axis = axis;
        this.axisTick = axisTick;
    }

    public Font getFont() {
        return font;
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void paint(Graphics2D g) {

        g.setColor(foreground);

        if (axis.getDirection() == Axis.Direction.Y) { // Y-Axis

            int xOffset = (int) (axis.getAxisTitle().getBounds().getX() + axis.getAxisTitle().getBounds().getWidth());
            int yOffset = (int) (axis.getPaintZone().getY());
            int maxTickLabelWidth = 0;
            for (int i = 0; i < axisTick.getTickLabels().size(); i++) {

                String tickLabel = axisTick.getTickLabels().get(i);
                int tickLocation = axisTick.getTickLocations().get(i);

                TextLayout layout = new TextLayout(tickLabel, font, new FontRenderContext(null, true, false));
                Rectangle tickLabelBounds = layout.getPixelBounds(null, 0, 0);
                layout.draw(g, xOffset, (int) (yOffset + axis.getPaintZone().getHeight() - tickLocation + tickLabelBounds.getHeight() / 2.0));

                if (tickLabelBounds.getWidth() > maxTickLabelWidth) {
                    maxTickLabelWidth = (int) tickLabelBounds.getWidth();
                }
            }

            // bounds
            bounds = new Rectangle(xOffset, yOffset, maxTickLabelWidth, (int) axis.getPaintZone().getHeight());
            // g.setColor(Color.blue);
            // g.draw(bounds);

        } else { // X-Axis

            int xOffset = (int) (axis.getPaintZone().getX());
            int yOffset = (int) (axis.getAxisTitle().getBounds().getY());
            int maxTickLabelHeight = 0;
            for (int i = 0; i < axisTick.getTickLabels().size(); i++) {

                String tickLabel = axisTick.getTickLabels().get(i);
                int tickLocation = axisTick.getTickLocations().get(i);

                TextLayout layout = new TextLayout(tickLabel, font, new FontRenderContext(null, true, false));
                Rectangle tickLabelBounds = layout.getPixelBounds(null, 0, 0);
                layout.draw(g, (int) (xOffset + tickLocation - tickLabelBounds.getWidth() / 2.0), yOffset);

                if (tickLabelBounds.getHeight() > maxTickLabelHeight) {
                    maxTickLabelHeight = (int) tickLabelBounds.getHeight();
                }
            }

            // bounds
            bounds = new Rectangle(xOffset, yOffset - maxTickLabelHeight, (int) axis.getPaintZone().getWidth(), maxTickLabelHeight);
            // g.setColor(Color.blue);
            // g.draw(bounds);

        }

    }
}
