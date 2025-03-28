package meldexun.nothirium.mc.vertex;

import meldexun.memoryutil.UnsafeUtil;
import meldexun.nothirium.mc.integration.CensoredASM;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import sun.misc.Unsafe;

public class TextureCoordinateUploader {

	private static final VertexConsumer FLOAT = new VertexConsumer() {
		@Override
		public void tex(ExtendedBufferBuilder buffer, double u, double v) {
			Unsafe unsafe = UnsafeUtil.UNSAFE;
			long address = buffer.getAddress() + buffer.getOffset();
			unsafe.putFloat(address, (float) u);
			unsafe.putFloat(address + 4, (float) v);
			CensoredASM.triggerSpriteOnBufferAccess(buffer, (float)u, (float)v);
		}

		@Override
		public void lightmap(ExtendedBufferBuilder buffer, int skyLight, int blockLight) {
			Unsafe unsafe = UnsafeUtil.UNSAFE;
			long address = buffer.getAddress() + buffer.getOffset();
			unsafe.putFloat(address, (float) skyLight);
			unsafe.putFloat(address + 4, (float) blockLight);
		}
	};
	private static final VertexConsumer INT = new VertexConsumer() {
		@Override
		public void tex(ExtendedBufferBuilder buffer, double u, double v) {
			Unsafe unsafe = UnsafeUtil.UNSAFE;
			long address = buffer.getAddress() + buffer.getOffset();
			unsafe.putInt(address, (int) u);
			unsafe.putInt(address + 4, (int) v);
		}

		@Override
		public void lightmap(ExtendedBufferBuilder buffer, int skyLight, int blockLight) {
			Unsafe unsafe = UnsafeUtil.UNSAFE;
			long address = buffer.getAddress() + buffer.getOffset();
			unsafe.putInt(address, skyLight);
			unsafe.putInt(address + 4, blockLight);
		}
	};
	private static final VertexConsumer SHORT = new VertexConsumer() {
		@Override
		public void tex(ExtendedBufferBuilder buffer, double u, double v) {
			Unsafe unsafe = UnsafeUtil.UNSAFE;
			long address = buffer.getAddress() + buffer.getOffset();
			unsafe.putShort(address, (short) ((int) v));
			unsafe.putShort(address + 2, (short) ((int) u));
		}

		@Override
		public void lightmap(ExtendedBufferBuilder buffer, int skyLight, int blockLight) {
			Unsafe unsafe = UnsafeUtil.UNSAFE;
			long address = buffer.getAddress() + buffer.getOffset();
			unsafe.putShort(address, (short) blockLight);
			unsafe.putShort(address + 2, (short) skyLight);
		}
	};
	private static final VertexConsumer BYTE = new VertexConsumer() {
		@Override
		public void tex(ExtendedBufferBuilder buffer, double u, double v) {
			Unsafe unsafe = UnsafeUtil.UNSAFE;
			long address = buffer.getAddress() + buffer.getOffset();
			unsafe.putByte(address, (byte) ((int) v));
			unsafe.putByte(address + 1, (byte) ((int) u));
		}

		@Override
		public void lightmap(ExtendedBufferBuilder buffer, int skyLight, int blockLight) {
			Unsafe unsafe = UnsafeUtil.UNSAFE;
			long address = buffer.getAddress() + buffer.getOffset();
			unsafe.putByte(address, (byte) blockLight);
			unsafe.putByte(address + 1, (byte) skyLight);
		}
	};

	public static VertexConsumer fromType(VertexFormatElement.EnumType type) {
		switch (type) {
		case FLOAT:
			return FLOAT;
		case UINT:
		case INT:
			return INT;
		case USHORT:
		case SHORT:
			return SHORT;
		case UBYTE:
		case BYTE:
			return BYTE;
		default:
			return null;
		}
	}

}
