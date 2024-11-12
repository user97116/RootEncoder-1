/*
 * Copyright (C) 2024 pedroSG94.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pedro.encoder.utils.gl;

import com.pedro.encoder.utils.ViewPort;

import kotlin.Pair;

/**
 * Created by pedro on 22/03/19.
 */

public class SizeCalculator {
    public static ViewPort calculateViewPort(AspectRatioMode mode, int previewWidth,
                                             int previewHeight, int streamWidth, int streamHeight) {
        // Apply width scaling by 0.6 to the original stream width
        int scaledStreamWidth = (int) (streamWidth * 0.3);

        // Calculate the aspect ratio based on the scaled width
        float streamAspectRatio = (float) scaledStreamWidth / (float) streamHeight;
        float previewAspectRatio = (float) previewWidth / (float) previewHeight;
        int xo = 0;
        int yo = 0;
        int xf = previewWidth;
        int yf = previewHeight;

        // Crop mode logic (to ensure no black bars)
        if (streamAspectRatio > previewAspectRatio) {
            // Stream is wider than preview, scale to match the preview width
            xf = previewWidth;  // Match the width of the preview
            yf = (int) (previewWidth / streamAspectRatio);  // Adjust the height to maintain the aspect ratio
            yo = (previewHeight - yf) / 2;  // Center the image vertically
        } else {
            // Stream is taller than preview, scale to match the preview height
            yf = previewHeight;  // Match the height of the preview
            xf = (int) (previewHeight * streamAspectRatio);  // Adjust the width to maintain the aspect ratio

            // If the width exceeds the preview width, we need to crop
            if (xf < previewWidth) {
                // Stream doesn't fill the preview width, adjust to fill the preview width
                xf = previewWidth;  // Match the width of the preview
                yf = (int) (previewWidth / streamAspectRatio);  // Adjust the height accordingly
                yo = (previewHeight - yf) / 2;  // Center the image vertically
            } else {
                xo = (previewWidth - xf) / 2;  // Center the image horizontally
            }
        }

        // Return the final calculated ViewPort
        return new ViewPort(0, 0, previewWidth, previewHeight);
    }

    public static ViewPort calculateViewPortByHeight(AspectRatioMode mode, int previewWidth,
                                             int previewHeight, int streamWidth, int streamHeight) {
        // Apply height scaling by 1.3 to the original stream height
        int scaledStreamHeight = (int) (streamHeight * 1.3);

        // Calculate the aspect ratio based on the scaled height
        float streamAspectRatio = (float) streamWidth / (float) scaledStreamHeight;
        float previewAspectRatio = (float) previewWidth / (float) previewHeight;
        int xo = 0;
        int yo = 0;
        int xf = previewWidth;
        int yf = previewHeight;

        // Crop mode logic (to ensure no black bars)
        if (streamAspectRatio > previewAspectRatio) {
            // Stream is wider than preview, scale to match the preview width
            xf = previewWidth;  // Match the width of the preview
            yf = (int) (previewWidth / streamAspectRatio);  // Adjust the height to maintain the aspect ratio
            yo = (previewHeight - yf) / 2;  // Center the image vertically
        } else {
            // Stream is taller than preview, scale to match the preview height
            yf = previewHeight;  // Match the height of the preview
            xf = (int) (previewHeight * streamAspectRatio);  // Adjust the width to maintain the aspect ratio

            // If the width exceeds the preview width, we need to crop
            if (xf < previewWidth) {
                // Stream doesn't fill the preview width, adjust to fill the preview width
                xf = previewWidth;  // Match the width of the preview
                yf = (int) (previewWidth / streamAspectRatio);  // Adjust the height accordingly
                yo = (previewHeight - yf) / 2;  // Center the image vertically
            } else {
                xo = (previewWidth - xf) / 2;  // Center the image horizontally
            }
        }

        // Return the final calculated ViewPort
        return new ViewPort(xo, yo, xf, yf);
    }

    public static ViewPort calculateViewPortEncoder(int streamWidth, int streamHeight, boolean isPortrait) {
        float factor = (float) streamWidth / (float) streamHeight;
        if (factor >= 1f) {
            if (isPortrait) {
                int width = (int) (streamHeight / factor);
                int oX = (streamWidth - width) / 2;
                return new ViewPort(oX, 0, width, streamHeight);
            } else {
                return new ViewPort(0, 0, streamWidth, streamHeight);
            }
        } else {
            if (isPortrait) {
                return new ViewPort(0, 0, streamWidth, streamHeight);
            } else {
                int height = (int) (streamWidth * factor);
                int oY = (streamHeight - height) / 2;
                return new ViewPort(0, oY, streamWidth, height);
            }
        }
    }

    public static Pair<Float, Float> calculateFlip(boolean flipStreamHorizontal, boolean flipStreamVertical) {
        return new Pair<>(flipStreamHorizontal ? -1f : 1f, flipStreamVertical ? -1f : 1f);
    }
}