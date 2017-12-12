      {% loop in $fields %}
      //{$comment}
      {% if ($type == numeric) %}
      CAN_extract_numeric(data, {$pos}, {$len}, {$endian}, {$sgn}, {$scl}, {$off});
      {% elseIf ($type == bit) %}
      CAN_extract_bit(data, {$pos}, {$len}, {$endian}, {$bitPos});
      {% endif %}
      {% onEmpty %}
      {% endloop %}