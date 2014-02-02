file = File.open('level128.atlas')
contents = Array.new()
file.each {|line|
  contents.push(line)
}
file.close()
out = File.open('level128.atlas', "w")
out.write(contents.shift(4).to_s)
while contents.size() > 5
	group = contents.shift(7)
	group[6] = "  index: "+group[0].gsub(/[^0-9]/, '').to_i.to_s+"\n"
        group[0] = "level128\n"
	out.write(group.to_s)
end
