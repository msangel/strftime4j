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

assertEqual "%", (t.strftime "%_50E%")
#



# Groups of modifiers:
# 1) accept any amount of [-_0] as flags the latest of them take effect,
#    accept any amount of [^#] - having at least one of it
#    cause the string to be upper case
#    the [:] - cause all pattern treated as broken and so printed as source
#  Modifiers in input cause pattern treated as broken
#  <width> works well with padding flag
# Known conversions:
#    %a
#
