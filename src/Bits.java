/**
 * Class to perform common bit operations on bytes.
 * 
 * @author Mike Wallace
 * @version 1.0
 */
public final class Bits
{
  /**
   * Array of first 8 numbers, each with a different single bit set.
   */
  private static final int[] oneBitSet = {1,  // Only the 1st bit is set
                                          2,  // Only the 2nd bit is set
                                          4,  // Only the 3rd bit is set
                                          8,  // Only the 4th bit is set
                                          16,  // Only the 5th bit is set
                                          32,  // Only the 6th bit is set
                                          64,  // Only the 7th bit is set
                                          128};  // Only the 8th bit is set
  
  /**
   * Array of first 8 numbers, each with a different single bit not set.
   */
  private static final int[] oneBitNotSet = {254,  // All bits set but the 1st
                                             253,  // All bits set but the 2nd
                                             251,  // All bits set but the 3rd
                                             247,  // All bits set but the 4th
                                             239,  // All bits set but the 5th
                                             223,  // All bits set but the 6th
                                             191,  // All bits set but the 7th
                                             127}; // All bits set but the 8th
  
  
  /**
   * Default constructor.
   */
  private Bits()
  {
    super();
  }
  
  
  /**
   * Returns whether all of the bits in the byte are set, from position
   * pos1 to pos2 (inclusive).  The bits are numbered 0 to 7, with the
   * leftmost bit 0, and the rightmost bit 7.
   * 
   * @param b the byte to check
   * @return whether all bits are set
   */
  public static boolean areAllBitsSet(final byte b)
  {
    // Return whether all bits in b are set (Java stores negative
    // numbers in 2's complement)
    return (b == -1);
  }
  
  
  /**
   * Returns whether all of the bits in the byte are set, from position
   * pos1 to pos2 (inclusive).  The bits are numbered 0 to 7, with the
   * leftmost bit 0, and the rightmost bit 7.
   * 
   * @param b the byte to check
   * @param pos1 the starting bit position
   * @param pos2 the ending bit position
   * @return whether all bits from pos1 to pos2 are set
   */
  public static boolean areBitsSet(final byte b,
                                   final int pos1,
                                   final int pos2)
  {
    // Check the input
    if ((pos1 < 0) || (pos1 > 7) || (pos2 < 0) || (pos2 > 7) || (pos1 > pos2))
    {
      throw new RuntimeException("Illegal positions in areBitsSet()");
    }
    else if (b == 0)
    {
      // The byte is zero, so no bits are set
      return false;
    }
    
    // The return value; default to true
    boolean areSet = true;
    
    // Iterate over all of the specified bits
    for (int i = pos1; (i <= pos2) && (areSet); ++i)
    {
      // Check if the current bit is set
      areSet = isBitSet(b, i);
    }
    
    // Return whether all of the bits are set
    return areSet;
  }
  
  
  /**
   * Return whether a bit is set.  Pos is the position, 0-7,
   * starting with the left-most bit.
   *  
   * @param b the byte to check
   * @param pos the position of the bit in b to check (0-7)
   * @return whether bit in position pos is set
   */
  public static boolean isBitSet(final byte b,
                                 final int pos)
  {
    // Check the input
    if ((pos < 0) || (pos > 7))
    {
      throw new RuntimeException("Illegal position in isBitSet()");
    }
    else if (b == 0)
    {
      // The byte is zero, so no bits are set
      return false;
    }
    
    // Reverse the position (low bit is right-most, not left-most)
    final int normalizedPos = 7 - pos;
    
    // Save 2^normalizedPos from the pre-calculated array
    final int setBit = oneBitSet[normalizedPos];
    
    // Return if the bit is set
    return ((b & setBit) == setBit);
  }
  
  
  /**
   * Return the integer value from a subset of bits in a byte.
   * This extracts the bits from the byte and uses that subset
   * to create a new integer, which is returned.  Pos1 and pos2
   * must both be between zero and seven, with pos1 not greater
   * than pos2.  The bits in the byte are numbered zero to
   * seven, with the leftmost bit the first one (number zero).
   * When the return value is computed from the subset of bits,
   * the rightmost bit of that set is considered the first
   * bit (if on, it has a value of 1).
   * 
   * @param b the byte to get a value from
   * @param pos1 the starting bit position
   * @param pos2 the ending bit position
   * @return the integer created using the set of bits
   */
  public static int getIntFromByte(final byte b,
                                   final int pos1,
                                   final int pos2)
  {
    // Check the input
    if ((pos1 < 0) || (pos1 > 7) || (pos2 < 0) || (pos2 > 7) || (pos1 > pos2))
    {
      throw new RuntimeException("Illegal positions in getIntFromByte()");
    }
    
    // Declare the variable that will get returned
    int value = 0;
    
    // Iterate over the bits to see if they're set (do it backwards
    // since we're starting with the rightmost bit)
    for (int i = pos2; i >= pos1; --i)
    {
      // Check if the current bit is set
      if (isBitSet(b, i))
      {
        // It is set, so compute the position (distance to the left of pos2)
        int currPos = pos2 - i;
        
        // Increment value by the number with only the current bit set
        value += (oneBitSet[currPos]);
      }
    }
    
    // Return the computed value
    return value;
  }
  
  
  /**
   * Clear the bit in position pos in the byte.  The position
   * must be between zero and seven.  The leftmost bit is
   * position zero.
   * 
   * @param b the byte to modify
   * @param pos the position of the bit to clear
   * @return the modified byte
   */
  public static byte clearBit(final byte b,
                              final int pos)
  {
    // Check the input
    if ((pos < 0) || (pos > 7))
    {
      throw new RuntimeException("Illegal position in clearBit()");
    }
    
    // See if it's already clear
    if (!(isBitSet(b, pos)))
    {
      // It's clear, so return the original byte
      return b;
    }
    
    // Clear the bit in the new byte
    byte r = (byte) (b & oneBitNotSet[7 - pos]);
    
    // Return the new byte
    return r;
  }
  
  
  /**
   * Clear the bits in position pos1 to pos2 in the byte.  The
   * positions must be between zero and seven.  The leftmost bit is
   * position zero.  Pos1 must be less than or equal to pos2.
   * 
   * @param b the byte to modify
   * @param pos1 the starting position of the bit range to clear
   * @param pos2 the ending position of the bit range to clear
   * @return the modified byte
   */
  public static byte clearBits(final byte b,
                               final int pos1,
                               final int pos2)
  {
    // Check the input
    if ((pos1 < 0) || (pos1 > 7) || (pos2 < 0) || (pos2 > 7) || (pos1 > pos2))
    {
      throw new RuntimeException("Illegal positions in clearBits()");
    }
    else if (b == 0)
    {
      // The byte is zero, so all bits are already clear
      return b;
    }
    
    // Iterate over the range of bits and clear each one
    byte r = b;
    for (int i = pos1; (i <= pos2); ++i)
    {
      r = clearBit(r, i);
    }
    
    return r;
  }
  
  
  /**
   * Set the bit in position pos in the byte.  The position
   * must be between zero and seven.  The leftmost bit is
   * position zero.
   * 
   * @param b the byte to modify
   * @param pos the position of the bit to set
   * @return the modified byte
   */
  public static byte setBit(final byte b,
                            final int pos)
  {
    // Check the input
    if ((pos < 0) || (pos > 7))
    {
      throw new RuntimeException("Illegal position in setBit()");
    }
    else if (areAllBitsSet(b))
    {
      // All bits are set, so return the byte
      return b;
    }
    
    // See if it's already set
    if (isBitSet(b, pos))
    {
      // It's set, so return the original byte
      return b;
    }
    
    // Set the bit in the new byte
    byte r = (byte) (b | oneBitSet[7 - pos]);
    
    // Return the new byte
    return r;
  }
  
  
  /**
   * Set the bits in position pos1 to pos2 in the byte.  The
   * positions must be between zero and seven.  The leftmost bit is
   * position zero.  Pos1 must be less than or equal to pos2.
   * 
   * @param b the byte to modify
   * @param pos1 the starting position of the bit range to set
   * @param pos2 the ending position of the bit range to set
   * @return the modified byte
   */
  public static byte setBits(final byte b,
                             final int pos1,
                             final int pos2)
  {
    // Check the input
    if ((pos1 < 0) || (pos1 > 7) || (pos2 < 0) || (pos2 > 7) || (pos1 > pos2))
    {
      throw new RuntimeException("Illegal positions in setBits()");
    }
    else if (areAllBitsSet(b))
    {
      // All bits are set, so return the byte
      return b;
    }
    
    // Iterate over the range of bits and clear each one
    byte r = b;
    for (int i = pos1; (i <= pos2); ++i)
    {
      r = setBit(r, i);
    }
    
    return r;
  }
  
  
  /**
   * Flip the specified bit in the byte, and return the new
   * byte to the caller.  The position must be between zero
   * and seven, with the zero bit being the left-most bit.
   * 
   * @param b the byte to modify
   * @param pos the position of the bit to flip
   * @return the byte with the flipped bit
   */
  public static byte flipBit(final byte b,
                             final int pos)
  {
    // Check the input
    if ((pos < 0) || (pos > 7))
    {
      throw new RuntimeException("Illegal position in flipBit()");
    }
    
    // Use the exclusive-or on the byte with a value that has only
    // the specified bit set.  If the bit is set, it is cleared.
    // If it is cleared already, it is set.
    byte b2 = (byte) (b ^ (oneBitSet[7 - pos]));
    
    // Return the new byte
    return b2;
  }
  
  
  /**
   * Flip the specified bits in the byte, and return the new
   * byte to the caller.  The positions must be between zero
   * and seven, with the zero bit being the left-most bit.
   * Pos1 must be less or equal to pos2.
   * 
   * @param b the byte to modify
   * @param pos1 the starting position of the bits to flip
   * @param pos2 the ending position of the bits to flip
   * @return the byte with the flipped bit
   */
  public static byte flipBits(final byte b,
                              final int pos1,
                              final int pos2)
  {
    // Check the input
    if ((pos1 < 0) || (pos1 > 7) || (pos2 < 0) || (pos2 > 7) || (pos1 > pos2))
    {
      throw new RuntimeException("Illegal positions in flipBits()");
    }
    
    // This is the value we'll return
    byte value = b;
    
    // Iterate over the bit positions to flip
    for (int i = pos1; i <= pos2; ++i)
    {
      value = flipBit(value, i);
    }
    
    // Return the new value
    return value;
  }
  
  
  /**
   * Gets a specified byte from an integer.  The bytenum argument
   * must be between zero and three (inclusive).  The zero byte
   * is the rightmost byte of the value parameter.
   *
   * @param value the integer to extract a byte from
   * @param bytenum the byte to extract from value
   * @return byte number bytenum from value
   */
  public static byte getByteFromInt(final int value,
                                    final int bytenum)
  {
    if ((bytenum < 0) || (bytenum > 3))
    {
      throw new RuntimeException("The bit number parameter value is illegal");
    }
    
    return ((byte) (value >>> (bytenum * 8)));
  }
  
  
  /**
   * Returns the byte as an 8-digit binary string.  Two's
   * complement is used for negative numbers.  The returned
   * string will always be 8 characters long, with each
   * character either a 1 or a 0.
   * 
   * @param b the byte to use
   * @return b as an 8 digit binary string
   */
  public static String getByteAsBinaryString(final byte b)
  {
    StringBuilder sb = new StringBuilder(8);
    for (int i = 0; i < 8; ++i)
    {
      sb.append((isBitSet(b, i) ? '1' : '0'));
    }
    
    return (sb.toString());
  }
}
