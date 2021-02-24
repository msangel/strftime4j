#!/usr/bin/env ruby

def assertEqual(expected, real)
  if expected != real
    raise "{#{real}} is not {#{expected}}"
  end
end
#            year  month  day  hour  minutes  seconds  zone_offset
t = Time.new(2007, 1,     1,   1,    1,       1,       "-02:00")

# https://apidock.com/ruby/Time/strftime

# %<flags><width><modifier><conversion>
# flags:
# -  don't pad a numerical output
# _  use spaces for padding
# 0  use zeros for padding
# ^  upcase the result string
# #  change case
# :  use colons for %z
#
# The modifiers are “E” and “O”. They are ignored.
#
# %a - The abbreviated weekday name (“Sun”)
assertEqual "Mon", (t.strftime "%a")
assertEqual "MON", (t.strftime "%0^a")
assertEqual "Mon", (t.strftime "%-0--_0___-0-a")
assertEqual "MON", (t.strftime "%-0--_0_^__-0-a")
assertEqual "MON", (t.strftime "%-0--_0_#__-0-a")
assertEqual "%:a", (t.strftime "%:a")
assertEqual "%Ea", (t.strftime "%Ea")
assertEqual "%Oa", (t.strftime "%Oa")
assertEqual "  Mon", (t.strftime "%_5a")
assertEqual "00Mon", (t.strftime "%0_0_05a")
assertEqual "Mon", (t.strftime "%0_0_0-5a")

# pp t.strftime "%_4a"
assertEqual "Mon Jan  1 01:01:01 2007", (t.strftime "%c")
assertEqual "MON JAN  1 01:01:01 2007", (t.strftime "%^c")


# assertEqual "%", (t.strftime "%_50E%")
assertEqual "20", (t.strftime "%C")
assertEqual "  20", (t.strftime "%_4C")
assertEqual "01", (t.strftime "%d")
assertEqual "1", (t.strftime "%-d")
assertEqual " 1", (t.strftime "%_d")
assertEqual "07", (t.strftime "%g")
assertEqual "7", (t.strftime "%-g")
assertEqual " 7", (t.strftime "%_g")
assertEqual "2007", (t.strftime "%G")
assertEqual "%o", (t.strftime "%o")
assertEqual "%O", (t.strftime "%O")
assertEqual "00", (t.strftime "%U")
pp t.strftime "%v"

# Groups of modifiers:
# 1) accept any amount of [-_0] as flags the latest of them take effect,
#    accept any amount of [^#] - having at least one of it
#    cause the string to be upper case
#    the [:] - cause all pattern treated as broken and so printed as source
#  Modifiers in input cause pattern treated as broken
#  <width> works well with padding flag
# Known conversions:
#    %a
# 2) literal pattern:
#    any flag cause this to be rendered as text node
# Known conversions:
#    %%, %n
# 3) combination patterns, just a shortcut for frequently-used group of patterns:
# Known conversions:
#    %c
#
