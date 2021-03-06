package cn.co.willow.android.ultimate.gpuimage.core_render_filter.filter_3x3_sampling;

/**
 * 索贝尔算子边缘检测滤镜
 */
public class GPUImageDirectionalSobelEdgeDetectionFilter extends GPUImage3x3TextureSamplingFilter {
    public static final String DIRECTIONAL_SOBEL_EDGE_DETECTION_FRAGMENT_SHADER = "" +
            "precision mediump float;\n" +
            "\n" +
            "varying vec2 textureCoordinate;\n" +
            "varying vec2 leftTextureCoordinate;\n" +
            "varying vec2 rightTextureCoordinate;\n" +
            "\n" +
            "varying vec2 topTextureCoordinate;\n" +
            "varying vec2 topLeftTextureCoordinate;\n" +
            "varying vec2 topRightTextureCoordinate;\n" +
            "\n" +
            "varying vec2 bottomTextureCoordinate;\n" +
            "varying vec2 bottomLeftTextureCoordinate;\n" +
            "varying vec2 bottomRightTextureCoordinate;\n" +
            "\n" +
            "uniform sampler2D inputImageTexture;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    float bottomLeftIntensity = texture2D(inputImageTexture, bottomLeftTextureCoordinate).r;\n" +
            "    float topRightIntensity = texture2D(inputImageTexture, topRightTextureCoordinate).r;\n" +
            "    float topLeftIntensity = texture2D(inputImageTexture, topLeftTextureCoordinate).r;\n" +
            "    float bottomRightIntensity = texture2D(inputImageTexture, bottomRightTextureCoordinate).r;\n" +
            "    float leftIntensity = texture2D(inputImageTexture, leftTextureCoordinate).r;\n" +
            "    float rightIntensity = texture2D(inputImageTexture, rightTextureCoordinate).r;\n" +
            "    float bottomIntensity = texture2D(inputImageTexture, bottomTextureCoordinate).r;\n" +
            "    float topIntensity = texture2D(inputImageTexture, topTextureCoordinate).r;\n" +
            "\n" +
            "    vec2 gradientDirection;\n" +
            "    gradientDirection.x = -bottomLeftIntensity - 2.0 * leftIntensity - topLeftIntensity + bottomRightIntensity + 2.0 * rightIntensity + topRightIntensity;\n" +
            "    gradientDirection.y = -topLeftIntensity - 2.0 * topIntensity - topRightIntensity + bottomLeftIntensity + 2.0 * bottomIntensity + bottomRightIntensity;\n" +
            "\n" +
            "    float gradientMagnitude = length(gradientDirection);\n" +
            "    vec2 normalizedDirection = normalize(gradientDirection);\n" +
            "    normalizedDirection = sign(normalizedDirection) * floor(abs(normalizedDirection) + 0.617316); // Offset by 1-sin(pi/8) to set to 0 if near axis, 1 if away\n" +
            "    normalizedDirection = (normalizedDirection + 1.0) * 0.5; // Place -1.0 - 1.0 within 0 - 1.0\n" +
            "\n" +
            "    gl_FragColor = vec4(gradientMagnitude, normalizedDirection.x, normalizedDirection.y, 1.0);\n" +
            "}";

    public GPUImageDirectionalSobelEdgeDetectionFilter() {
        super(DIRECTIONAL_SOBEL_EDGE_DETECTION_FRAGMENT_SHADER);
    }
}
