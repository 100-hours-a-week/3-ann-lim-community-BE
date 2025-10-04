package com.kakaotechbootcamp.community.validation;

import com.kakaotechbootcamp.community.validation.group.FormatGroup;
import com.kakaotechbootcamp.community.validation.group.LengthGroup;
import com.kakaotechbootcamp.community.validation.group.RequiredGroup;
import jakarta.validation.GroupSequence;

@GroupSequence({RequiredGroup.class, FormatGroup.class, LengthGroup.class})
public interface ValidationOrder {}
