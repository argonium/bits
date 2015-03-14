# bits
Bits is a single Java class that makes it very easy to perform bit-related operations on a byte. The following operations are supported:

* Clear a bit or a range of bits
* Set a bit or a range of bits
* Check if a bit (or range of bits) is set
* Flip a bit or a range of bits
* Get a particular byte from an integer
* Convert a byte into a binary string, using 2's complement for negative values
* Extract a range of bits from a byte and generate an integer from that bitset

The following is a list of the methods exposed by this class:

````
  public static boolean isBitSet(final byte b,
                                 final int pos)
  
  public static boolean areBitsSet(final byte b,
                                   final int pos1,
                                   final int pos2)
  
  public static boolean areAllBitsSet(final byte b)
  
  public static byte clearBit(final byte b,
                              final int pos)
  
  public static byte clearBits(final byte b,
                               final int pos1,
                               final int pos2)
  
  public static byte setBit(final byte b,
                            final int pos)
  
  public static byte setBits(final byte b,
                             final int pos1,
                             final int pos2)
  
  public static byte flipBit(final byte b,
                             final int pos)
  
  public static byte flipBits(final byte b,
                              final int pos1,
                              final int pos2)
  
  public static byte getByteFromInt(final int value,
                                    final int bytenum)
  
  public static String getByteAsBinaryString(final byte b)
  
  public static int getIntFromByte(final byte b,
                                   final int pos1,
                                   final int pos2)
````

The class has Javadocs for each method and its parameters and return value, so the code should be easy to use. The position parameters (pos, pos1 and pos2) are explained there as well.

The code is released under the MIT license.
